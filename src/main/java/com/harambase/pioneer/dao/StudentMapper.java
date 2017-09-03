package com.harambase.pioneer.dao;

import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.pojo.dto.StudentView;
import org.springframework.stereotype.Component;

@Component
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    StudentView transcriptDetail(String studentid);
}