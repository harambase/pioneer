package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query("select count(person.userId) as count from Person person where person.type like concat('%',?1,'%') and person.status=?2")
    int countByType(String type);

    List<Contract> findByInitDate(String initDate);

    int countById(Integer id);

    Contract findByContractId(String contractId);
}
