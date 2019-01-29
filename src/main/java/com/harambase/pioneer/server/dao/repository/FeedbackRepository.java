package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    int countByFacultyIdAndInfo(String facultyId, String info);

    int countByInfo(String info);

    Feedback getByFacultyId(String facultyId);

    List<Feedback> findAllByInfo(String info);
}
