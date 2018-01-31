package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.constant.FlagDict;
import com.harambase.pioneer.server.RequestServer;
import com.harambase.pioneer.server.pojo.base.TempAdvise;
import com.harambase.pioneer.server.pojo.base.TempCourse;
import com.harambase.pioneer.server.pojo.base.TempUser;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedHashMap;

@Service
public class RequestService{
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RequestServer requestServer;

    @Autowired
    public RequestService(RequestServer requestServer) {
        this.requestServer = requestServer;
    }

    
    public HaramMessage deleteTempUser(Integer id) {
        try {
            return requestServer.removeUserRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage registerNewUser(JSONObject jsonObject) {
        try {
            return requestServer.register(jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage updateTempUser(Integer id, TempUser tempUser) {
        try {
            return requestServer.updateRequest(id, tempUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage tempUserList(int start, int length, String search, String order, String orderColumn, String viewStatus) {
        try {
            return requestServer.userList(start, length, search, order, orderColumn, viewStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage updateTempCourse(Integer id, TempCourse tempCourse) {
        try {
            return requestServer.updateCourseRequest(id, tempCourse);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage registerNewCourse(String facultyId, JSONObject jsonObject) {
        try {
            return requestServer.registerNewCourse(facultyId, jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage deleteTempCourse(Integer id) {
        try {
            return requestServer.removeCourseRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage tempCourseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String facultyId) {
        try {
            return requestServer.courseList(start, length, search, order, orderCol, viewStatus, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage getTempUser(Integer id) {
        try {
            return requestServer.getUserRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage getTempCourse(Integer id) {
        try {
            return requestServer.getCourseRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage uploadCourseInfo(Integer id, MultipartFile file) {
        HaramMessage message = new HaramMessage();
        JSONObject jsonObject = new JSONObject();

        try {
            //todo: LinkedHashMap->tempCourse
            LinkedHashMap tempCourseMap = (LinkedHashMap) requestServer.getCourseRequest(id).getData();
            TempCourse tempCourse = new TempCourse();
            BeanUtils.populate(tempCourse, tempCourseMap);

            //处理老的文件
            JSONObject courseJson = JSONObject.parseObject(tempCourse.getCourseJson());

            if (StringUtils.isNotEmpty(courseJson.getString("courseInfo"))) {
                String oldInfoPath = (JSON.parseObject(courseJson.getString("courseInfo"))).getString("path");
                File oldFile = new File(Config.TEMP_FILE_PATH + oldInfoPath);
                oldFile.delete();
            }

            String fileUri = FileUtil.uploadFileToPath(file, "/document/courseInfo");
            String name = file.getOriginalFilename();

            jsonObject.put("name", name);
            jsonObject.put("size", file.getSize());
            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
            jsonObject.put("path", fileUri);

            courseJson.put("courseInfo", jsonObject.toJSONString());

            tempCourse.setCourseJson(courseJson.toJSONString());
            message = requestServer.updateCourseRequest(id, tempCourse);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(FlagDict.FAIL.getV());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(jsonObject);
        return message;
    }

    
    public HaramMessage registerTempAdvise(String studentId, JSONObject jsonObject) {
        try {
            return requestServer.newAdvisorRequest(studentId, jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage deleteTempAdviseById(Integer id) {
        try {
            return requestServer.removeAdvisorRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage getTempAdvise(Integer id) {
        try {
            return requestServer.getAdviseRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage tempAdviseList(Integer start, Integer length, String search, String order, String orderCol) {
        try {
            return requestServer.adviseList(start, length, search, order, orderCol);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage updateTempAdvise(Integer id, TempAdvise tempAdvise) {
        try {
            return requestServer.updateAdviseRequest(id, tempAdvise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
