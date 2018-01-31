package com.harambase.pioneer.security.factory;

import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.security.ShiroUser;
import com.harambase.pioneer.security.SpringContextHolder;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.RoleServer;
import com.harambase.pioneer.common.support.util.CollectionUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@DependsOn("springContextHolder")
public class ShiroServiceImpl implements ShiroService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoleServer roleServer;
    private final PersonServer personServer;

    @Autowired
    public ShiroServiceImpl(RoleServer roleServer, PersonServer personServer) {
        this.personServer = personServer;
        this.roleServer = roleServer;
    }

    public static ShiroService me() {
        return SpringContextHolder.getBean(ShiroService.class);
    }

    @Override
    public Person user(String userid) {

        LinkedHashMap<String, Object> personMap = (LinkedHashMap) personServer.get(IP, PORT, userid).getData();

        // 账号不存在
        if (personMap == null)
            throw new CredentialsException();

        // 账号被冻结
        if (personMap.get("status").equals("0")) {
            throw new LockedAccountException();
        }
        Person person = new Person();

        try {
            BeanUtils.populate(person, personMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return person;
    }

    @Override
    public ShiroUser shiroUser(Person user) {

        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setUserId(user.getUserId());    // 用户id
        shiroUser.setUsername(user.getUsername());// 用户名

        Integer[] roleArray = CollectionUtil.toIntArray("/", user.getRoleId());// 角色集

        List<Integer> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<String> roleCodeList = new ArrayList<>();

        for (int roleId : roleArray) {
            roleList.add(roleId);
            LinkedHashMap<String, String> roleMap = (LinkedHashMap<String, String>) roleServer.findRoleByRoleid(IP, PORT, roleId).getData();
            roleNameList.add(roleMap.get("roleName"));
            roleCodeList.add(roleMap.get("roleCode"));
        }

        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);
        shiroUser.setRoleCodes(roleCodeList);

        return shiroUser;
    }

    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        LinkedHashMap<String, String> roleMap = (LinkedHashMap<String, String>) roleServer.findRoleByRoleid(IP, PORT, roleId).getData();
        return roleMap.get("roleName");
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, Person user, String realmName) {
        String source = user.getPassword();
        return new SimpleAuthenticationInfo(shiroUser, source, realmName);
        // 密码加盐处理
        //String credentials = user.getPassword();
        //ByteSource credentialsSalt = new Md5Hash(source);

    }

}
