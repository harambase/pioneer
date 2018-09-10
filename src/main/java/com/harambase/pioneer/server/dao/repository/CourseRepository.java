package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findCourseByFacultyId(String facultyId);

    int countCourseByCrn(String crn);

    Course findByCrn(String crn);

    @Query("select distinct c.info from Course c where c.name like concat('%',?1,'%') or c.crn like concat('%',?1,'%') or c.info like concat('%',?1,'%') ")
    List<String> getInfoList(String search);
}