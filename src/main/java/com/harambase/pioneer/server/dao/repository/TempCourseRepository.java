package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.TempCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempCourseRepository extends JpaRepository<TempCourse, Integer> {
    int countById(Integer id);
}
