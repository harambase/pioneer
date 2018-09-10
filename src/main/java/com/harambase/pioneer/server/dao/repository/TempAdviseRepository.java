package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.TempAdvise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempAdviseRepository extends JpaRepository<TempAdvise, Integer> {
    int countById(Integer id);

    TempAdvise findByStudentId(String studentId);

    int countByStudentIdAndInfo(String studentId, String info);
}
