package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    int countById(Integer id);
}