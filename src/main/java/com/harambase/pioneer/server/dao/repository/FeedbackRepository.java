package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    int countByFacultyIdAndInfo(String facultyId, String info);
}
