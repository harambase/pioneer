package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.*;
import com.harambase.pioneer.server.dao.base.PersonDao;
import com.harambase.pioneer.server.dao.repository.*;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.server.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PersonRepository personRepository;
    private final AdviseRepository adviseRepository;
    private final StudentRepository studentRepository;
    private final TranscriptRepository transcriptRepository;
    private final CourseRepository courseRepository;

    //Only for search and paging functionality
    private final PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, AdviseRepository adviseRepository,
                             StudentRepository studentRepository, CourseRepository courseRepository,
                             TranscriptRepository transcriptRepository, PersonDao personDao) {
        this.personRepository = personRepository;
        this.adviseRepository = adviseRepository;
        this.transcriptRepository = transcriptRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.personDao = personDao;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public ResultMap updateLastLoginTime(String keyword) {
        Person person = personRepository.findByQqOrUserIdOrUsername(keyword).get(0);
        person.setLastLoginTime(DateUtil.DateToStr(new Date()));
        Person newPerson = personRepository.save(person);
        return newPerson != null ? ReturnMsgUtil.success(newPerson) : ReturnMsgUtil.fail();
    }

    @Override
    public ResultMap getByKeyword(String keyword) {
        try {
            Person user = personRepository.findByQqOrUserIdOrUsername(keyword).get(0);
            return user != null ? ReturnMsgUtil.success(user) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap login(Person person) {
        try {
            Person user = personRepository.findByUserIdAndPassword(person.getUserId(), person.getPassword());
            if (user != null) {
                String status = user.getStatus();
                if (status.equals("1")) {
                    user.setLastLoginTime(DateUtil.DateToStr(new Date()));
                    personRepository.save(user);
                    return ReturnMsgUtil.success(user);
                }

                return ReturnMsgUtil.custom(SystemConst.USER_DISABLED);
            }
            return ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap addUser(Person person) {

        try {
            String userId, password;
            String info = person.getInfo();

            List<Person> people = personRepository.findByInfo(info);

            userId = IDUtil.genUserID(info);

            for (int i = 0; i < people.size(); i++) {
                Person p = people.get(i);
                if (userId.equals(p.getUserId())) {
                    userId = IDUtil.genUserID(info);
                    i = 0;
                }
            }
            person.setUserId(userId);

            if (StringUtils.isEmpty(person.getPassword())) {
                password = "Pioneer" + userId;
                person.setPassword(passwordEncoder().encode(password));
            }

            String firstPY = Pinyin4jUtil.converterToFirstSpell(person.getLastName());
            String lastPY = Pinyin4jUtil.converterToFirstSpell(person.getFirstName());
            String username = lastPY + firstPY + userId.substring(7, 10);

            person.setUsername(username);
            person.setCreateTime(DateUtil.DateToStr(new Date()));
            person.setUpdateTime(DateUtil.DateToStr(new Date()));

            if (StringUtils.isEmpty(person.getStatus()))
                person.setStatus("1");

            Person newPerson = personRepository.save(person);

            if (person.getType().contains("s")) {
                Student student = new Student();
                student.setStudentId(userId);
                student.setMaxCredits(12);
                student.setUpdateTime(DateUtil.DateToStr(new Date()));
                studentRepository.save(student);
            }

            return newPerson != null ? ReturnMsgUtil.success(newPerson) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    @Override
    public ResultMap removeUser(String userId) {

        try {
            if (userId.equals(IDUtil.ROOT))
                return ReturnMsgUtil.custom(SystemConst.DELETE_BLOCK);

            Person person = personRepository.findOne(userId);
            if (person == null) {
                return ReturnMsgUtil.fail();
            }

            if (person.getType().contains("s")) {
                studentRepository.delete(userId);
                transcriptRepository.deleteTranscriptByStudentId(person.getUserId());
            }

            adviseRepository.deleteByStudentIdOrFacultyId(person.getUserId(), person.getUserId());

            if (person.getType().contains("f")) {
                List<Course> courseList = courseRepository.findCourseByFacultyId(person.getUserId());

                for (Course c : courseList) {
                    String opTime = DateUtil.DateToStr(new Date());
                    c.setFacultyId(IDUtil.ROOT);
                    c.setComment(person.getLastName() + "," + person.getFirstName() + "老师被删除, 删除时间：" + opTime);
                    c.setUpdateTime(DateUtil.DateToStr(new Date()));
                    courseRepository.save(c);
                }
            }
            personRepository.delete(person);
            int count = personRepository.countByUserId(userId);
            return count == 0 ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap update(String userId, Person person) {
        try {
            String type = person.getType();
            int count = studentRepository.countByStudentId(userId);

            if (type.contains("s") && count == 0) {
                Student student = new Student();
                student.setStudentId(userId);
                student.setMaxCredits(12);
                student.setUpdateTime(DateUtil.DateToStr(new Date()));
                studentRepository.save(student);
            }

            if (!type.contains("s") && count != 0) {
                studentRepository.delete(userId);
            }

            person.setUserId(userId);
            person.setUpdateTime(DateUtil.DateToStr(new Date()));
            Person newPerson = personRepository.save(person);
            return newPerson != null ? ReturnMsgUtil.success(newPerson) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap getUser(String userId) {
        try {
            Person person = personRepository.findOne(userId);
            return ReturnMsgUtil.success(person);
        } catch (Exception e) {
            logger.error(e.toString());
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap userList(String currentPage, String pageSize, String search, String order, String orderColumn,
                              String type, String status, String role) {
        ResultMap message = new ResultMap();
        try {

            long totalSize = personDao.getCountByMapPageSearchOrdered(search, type, status, role);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<Person> personList = personDao.getByMapPageSearchOrdered(page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, type, status, role);

            message.setData(personList);
            message.put("page", page);
            message.setMsg(SystemConst.SUCCESS.getMsg());
            message.setCode(SystemConst.SUCCESS.getCode());
            return message;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    @Override
    public ResultMap listUsers(String search, String type, String status, String role, String maxLength) {
        try {
            List<Person> users = personDao.getPersonBySearch(search, type, status, role, maxLength);
            return ReturnMsgUtil.success(users);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap countActivePerson(String type) {
        try {
            int count = personRepository.countByTypeAndStatus(type, "1");
            return ReturnMsgUtil.success(count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
