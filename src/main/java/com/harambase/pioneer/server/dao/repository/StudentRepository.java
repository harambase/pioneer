package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    int countByStudentId(String userId);
}