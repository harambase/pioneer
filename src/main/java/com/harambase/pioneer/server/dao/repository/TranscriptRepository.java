package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Integer> {

    int countByStudentIdAndCrnAndComplete(String studentId, String crn, String complete);

    int countByStudentIdAndCrn(String studentId, String crn);

    void deleteTranscriptByStudentIdAndCrn(String studentId, String crn);

    void deleteTranscriptByStudentId(String userId);

    void deleteTranscriptByStudentIdAndInfo(String studentId, String info);

    List<Transcript> findByStudentIdAndInfo(String studentId, String info);
}
