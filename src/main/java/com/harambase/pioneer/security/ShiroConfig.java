package com.harambase.pioneer.security;

import com.harambase.pioneer.security.properties.ShiroSessionListener;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



@Configuration
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        /**
         * 默认的登陆访问url
         */
        shiroFilter.setLoginUrl("/index");
        /**
         * 登陆成功后跳转的url
         */
        shiroFilter.setSuccessUrl("/welcome");
        /**
         * 没有权限跳转的url
         */
        shiroFilter.setUnauthorizedUrl("/403");
        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         */
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("/static/styles/**", "anon");
        hashMap.put("/static/scripts/**", "anon");
        hashMap.put("/static/plugins/**", "anon");
        hashMap.put("/static/images/**", "anon");
        hashMap.put("/static/fonts/**", "anon");
        hashMap.put("/index", "anon");
        hashMap.put("/", "anon");
        hashMap.put("/admin/login", "anon");
        hashMap.put("/request/user/register", "anon");
        hashMap.put("/reg", "anon");
        hashMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(hashMap);

        return shiroFilter;
    }
    /**
     * 项目自定义的Realm
     */

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        ShiroDbRealm myRealm = new ShiroDbRealm();
        securityManager.setRealm(myRealm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

//    @Bean
//    public EhCacheManager ehCacheManager() {
//        EhCacheManager ehCacheManager = new EhCacheManager();
//        //ehCacheManager.setCacheManagerConfigFile("classpath:encache.xml");
//        return ehCacheManager;
//    }

}