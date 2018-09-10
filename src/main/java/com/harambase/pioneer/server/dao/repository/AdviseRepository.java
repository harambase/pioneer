package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Advise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdviseRepository extends JpaRepository<Advise, Integer> {

    void deleteByStudentIdOrFacultyId(String studentId, String facultyId);

    Advise findOneByStudentId(String studentId);

    List<Advise> findByFacultyId(String facultyId);

    int countByFacultyIdAndStudentId(String facultyId, String studentId);

    int countById(Integer id);
}
