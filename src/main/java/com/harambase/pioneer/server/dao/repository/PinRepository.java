package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<Pin, Integer> {

    Pin findByPin(Integer pinNum);

    void deleteByInfo(String info);

    void deleteByPin(Integer pin);

    int countByPin(Integer pin);

    @Query("select distinct info from Pin")
    List<String> findInfo();

    int countByInfoAndRole(String info, int role);

    int countByInfo(String info);

    int countByInfoAndOwnerIdAndRole(String info, String userId, int role);
}
