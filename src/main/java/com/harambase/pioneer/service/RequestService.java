package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.pojo.base.TempAdvise;
import com.harambase.pioneer.server.pojo.base.TempCourse;
import com.harambase.pioneer.server.pojo.base.TempUser;
import org.springframework.web.multipart.MultipartFile;

public interface RequestService {

    HaramMessage deleteTempUser(Integer id);

    HaramMessage registerNewUser(JSONObject jsonObject);

    HaramMessage updateTempUser(Integer id, TempUser tempUser);

    HaramMessage tempUserList(int start, int length, String search, String order, String orderCol, String viewStatus);

    HaramMessage updateTempCourse(Integer id, TempCourse tempCourse);

    HaramMessage registerNewCourse(String facultyId, JSONObject jsonObject);

    HaramMessage deleteTempCourse(Integer id);

    HaramMessage tempCourseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String facultyId);

    HaramMessage getTempUser(Integer id);

    HaramMessage getTempCourse(Integer id);

    HaramMessage uploadCourseInfo(Integer id, MultipartFile file);

    HaramMessage registerTempAdvise(String studentId, JSONObject jsonObject);

    HaramMessage deleteTempAdviseById(Integer id);

    HaramMessage getTempAdvise(Integer id);

    HaramMessage tempAdviseList(Integer start, Integer length, String search, String order, String orderCol);

    HaramMessage updateTempAdvise(Integer id, TempAdvise tempAdvise);
}
