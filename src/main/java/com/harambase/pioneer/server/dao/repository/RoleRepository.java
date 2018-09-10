package com.harambase.pioneer.server.dao.repository;

import com.harambase.pioneer.server.pojo.base.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleId(Integer roleId);
}
