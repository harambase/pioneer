package com.harambase.pioneer.security.factory;

import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.security.entity.ShiroUser;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.lang.reflect.InvocationTargetException;


public interface ShiroService {

    Person user(String userid);

    ShiroUser shiroUser(Person user);

    String findRoleNameByRoleId(Integer roleId);

    SimpleAuthenticationInfo info(ShiroUser shiroUser, Person user, String realmName);

}
