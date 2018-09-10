package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<TempUser, Integer> {

    int countById(Integer id);
}