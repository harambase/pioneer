package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Advise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AdviseRepository extends JpaRepository<Advise, Integer> {

    void deleteByStudentIdOrFacultyId(String studentId, String facultyId);

    Advise findOneByStudentId(String studentId);

    List<Advise> findByFacultyId(String facultyId);

    int countByFacultyIdAndStudentId(String facultyId, String studentId);

    @Query("select advise from Advise advise where advise.facultyId like concat('%',?1,'%') or advise.studentId like concat('%',?1,'%')")
    Optional<Advise> findByUserId(String userId);
}
