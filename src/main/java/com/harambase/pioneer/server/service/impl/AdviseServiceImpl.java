package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.AdviseDao;
import com.harambase.pioneer.server.dao.base.AdvisorDao;
import com.harambase.pioneer.server.dao.repository.AdviseRepository;
import com.harambase.pioneer.server.dao.repository.PersonRepository;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.view.AdviseView;
import com.harambase.pioneer.server.pojo.view.AdvisorView;
import com.harambase.pioneer.server.service.AdviseService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AdviseServiceImpl implements AdviseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdviseRepository adviseRepository;
    private final PersonRepository personRepository;

    private final AdviseDao adviseDao;
    private final AdvisorDao advisorDao;

    @Autowired
    public AdviseServiceImpl(AdviseRepository adviseRepository, AdviseDao adviseDao,
                             AdvisorDao advisorDao, PersonRepository personRepository) {
        this.adviseRepository = adviseRepository;
        this.personRepository = personRepository;
        this.adviseDao = adviseDao;
        this.advisorDao = advisorDao;
    }

    @Override
    public ResultMap advisingList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                  String studentId, String facultyId, String info) {
        try {
            ResultMap message = new ResultMap();

            long totalSize = adviseDao.getCountByMapPageSearchOrdered(facultyId, studentId, info, search);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<AdviseView> adviseViewList = adviseDao.getByMapPageSearchOrdered(facultyId, studentId, info, search,
                    page.getCurrentIndex(), page.getPageSize(), order, orderColumn);

            message.setData(adviseViewList);
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
    public ResultMap assignMentor(Advise advise) {
        try {

            int count = adviseRepository.countByFacultyIdAndStudentId(advise.getFacultyId(), advise.getStudentId());
            if (count != 0)
                return ReturnMsgUtil.custom(SystemConst.ADVISE_DUPLICATE);

            Advise oldAdvise = adviseRepository.findOneByStudentId(advise.getStudentId());
            if (oldAdvise != null) {
                oldAdvise.setStatus("0");
                adviseRepository.save(oldAdvise);
            }

            advise.setStatus("1");
            advise.setUpdateTime(DateUtil.DateToStr(new Date()));

            Advise newAdvise = adviseRepository.save(advise);
            return newAdvise != null ? ReturnMsgUtil.success(newAdvise) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    @Override
    public ResultMap removeMentor(Integer id) {
        try {
            adviseRepository.delete(id);
            int count = adviseRepository.countById(id);
            return count == 0 ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap updateAdvise(Integer id, Advise advise) {

        try {
            advise.setId(id);
            advise.setUpdateTime(DateUtil.DateToStr(new Date()));
            Advise newAdvise = adviseRepository.save(advise);
            return ReturnMsgUtil.success(newAdvise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap getMentor(Integer id) {
        try {
            AdviseView advise = adviseDao.findOne(id);
            return ReturnMsgUtil.success(advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap getAdviseByStudentId(String studentId) {
        try {
            Advise advise = adviseRepository.findOneByStudentId(studentId);
            return ReturnMsgUtil.success(advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap advisorList(String currentPage, String pageSize, String search, String order, String orderColumn, String status) {
        try {
            ResultMap message = new ResultMap();

            long totalSize = advisorDao.getAdvisorCountByMapPageSearchOrdered(search);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<AdvisorView> advisorViewList = advisorDao.getAdvisorByMapPageSearchOrdered(status, search,
                    page.getCurrentIndex(), page.getPageSize(), order, orderColumn);

            message.setData(advisorViewList);
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
    public ResultMap removeAdvisor(String userId) {
        try {
            String updateTime = DateUtil.DateToStr(new Date());
            List<Advise> adviseList = adviseRepository.findByFacultyId(userId);

            //先将之前的ADVIS关系变成0.
            for (Advise a : adviseList) {
                a.setStatus("0");
                a.setUpdateTime(updateTime);
                adviseRepository.save(a);
            }

            //删除导师权限7.
            Person person = personRepository.getOne(userId);
            String[] roleId = person.getRoleId().split("/");
            String roleIds = "";
            for (String role : roleId) {
                if (StringUtils.isNotEmpty(role) && !role.equals("7"))
                    roleIds += role + "/";
            }
            person.setRoleId(roleIds);
            person.setUpdateTime(updateTime);

            Person newPerson = personRepository.save(person);
            return newPerson != null ? ReturnMsgUtil.success(newPerson) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap addAdvisor(String userId) {
        try {
            Person person = personRepository.getOne(userId);
            String roleId = person.getRoleId() + "7/";
            person.setRoleId(roleId);
            person.setUpdateTime(DateUtil.DateToStr(new Date()));

            Person newPerson = personRepository.save(person);
            return newPerson != null ? ReturnMsgUtil.success(newPerson) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap getAdvisor(String userId) {
        try {
            AdvisorView advisor = advisorDao.findOne(userId);
            return advisor != null ? ReturnMsgUtil.success(advisor) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();

        }
    }

    @Override
    public ResultMap getAdviseViewByStudentId(String studentId) {
        try {
            AdviseView advise = adviseDao.getAdviseViewByStudentId(studentId);
            return advise != null ? ReturnMsgUtil.success(advise) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();

        }
    }
}
