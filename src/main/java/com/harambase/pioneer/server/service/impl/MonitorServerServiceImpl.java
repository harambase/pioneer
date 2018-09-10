package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.MapParam;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.AdvisorDao;
import com.harambase.pioneer.server.dao.base.CourseDao;
import com.harambase.pioneer.server.dao.repository.AdviseRepository;
import com.harambase.pioneer.server.dao.repository.PersonRepository;
import com.harambase.pioneer.server.dao.repository.TranscriptRepository;
import com.harambase.pioneer.server.helper.StaticGexfGraph;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.base.Transcript;
import com.harambase.pioneer.server.service.MonitorServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MonitorServerServiceImpl implements MonitorServerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PersonRepository personRepository;
    private final AdviseRepository adviseRepository;
    private final TranscriptRepository transcriptRepository;
    private final AdvisorDao advisorDao;

    private final CourseDao courseDao;

    @Autowired
    public MonitorServerServiceImpl(PersonRepository personRepository, AdviseRepository adviseRepository,
                                    CourseDao courseDao, TranscriptRepository transcriptRepository,
                                    AdvisorDao advisorDao) {
        this.personRepository = personRepository;
        this.adviseRepository = adviseRepository;
        this.transcriptRepository = transcriptRepository;
        this.courseDao = courseDao;
        this.advisorDao = advisorDao;
    }

    @Override
    public ResultMap userChart() {

        try {
            //统计用户种类
            List<Map<String, String>> data1 = new ArrayList<>();

            int s = personRepository.countByTypeAndStatus("s", "1");
            int f = personRepository.countByTypeAndStatus("f", "1");
            int a = personRepository.countByTypeAndStatus("a", "1");

            data1.add(MapParam.pieChartValue(String.valueOf(s), "Student"));
            data1.add(MapParam.pieChartValue(String.valueOf(f), "Faculty"));
            data1.add(MapParam.pieChartValue(String.valueOf(a), "Administrator"));


            //统计性别
            List<Map<String, String>> data2 = new ArrayList<>();
            int male = personRepository.countByGenderAndStatus("male", "1");
            int female = personRepository.countByGenderAndStatus("female", "1");

            data2.add(MapParam.pieChartValue(String.valueOf(male), "Male"));
            data2.add(MapParam.pieChartValue(String.valueOf(female), "Female"));

            ResultMap message = new ResultMap();
            message.put("dataBeast", data1);
            message.put("xAxisData", data2);

            return message;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap systemCount() {

        try {
            Map<String, Integer> data = new HashMap<>();

            int student = personRepository.countByTypeAndStatus("s", "1");
            int faculty = personRepository.countByTypeAndStatus("f", "1");
            int advisor = (int) advisorDao.count();
            int course = courseDao.countAllByStatus("1");

            data.put("student", student);
            data.put("faculty", faculty);
            data.put("course", course);
            data.put("advisor", advisor);

            return ReturnMsgUtil.success(data);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap relationChart() {

        try {
            List<Person> personList = personRepository.findAll();
            List<LinkedHashMap> courseViewList = courseDao.getByMapPageSearchOrdered("", "", "", "", 0, Integer.MAX_VALUE, "desc", "crn");
            List<Transcript> transcriptList = transcriptRepository.findAll();
            List<Advise> adviseList = adviseRepository.findAll();

            String xml = StaticGexfGraph.graphGenerator(personList, courseViewList, transcriptList, adviseList);

            return ReturnMsgUtil.success(xml);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
