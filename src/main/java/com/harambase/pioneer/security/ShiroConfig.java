package com.harambase.pioneer.security;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.harambase.pioneer.security.properties.PioneerProperties;
import com.harambase.pioneer.security.properties.ShiroSessionListener;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Configuration
public class ShiroConfig {

    /**
     * 安全管理器
     */
//    @Bean
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        ShiroDbRealm myRealm = new ShiroDbRealm();
//        securityManager.setRealm(myRealm);
//        securityManager.setSessionManager(sessionManager());
//        return securityManager;
//    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager rememberMeManager
                                                     //CacheManager cacheShiroManager,
                                                     //DefaultWebSessionManager defaultWebSessionManager
    ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.shiroDbRealm());
//        securityManager.setCacheManager(cacheShiroManager);
        securityManager.setRememberMeManager(rememberMeManager);
//        securityManager.setSessionManager(defaultWebSessionManager);
        return securityManager;
    }

//    /**
//     * session管理器
//     */
//    @Bean
//    public DefaultWebSessionManager defaultWebSessionManager(CacheManager cacheShiroManager, PioneerProperties pioneerProperties) {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setCacheManager(cacheShiroManager);
//        sessionManager.setSessionValidationInterval(pioneerProperties.getSessionValidationInterval() * 1000);
//        sessionManager.setGlobalSessionTimeout(pioneerProperties.getSessionInvalidateTime() * 1000);
//        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
//        cookie.setName("shiroAdminCookie");
//        cookie.setHttpOnly(true);
//        sessionManager.setSessionIdCookie(cookie);
//        return sessionManager;
//    }

//    /**
//     * 缓存管理器 使用Ehcache实现
//     */
//    @Bean
//    @Qualifier(value="cacheShiroManager")
//    public CacheManager getCacheShiroManager(final EhCacheManagerFactoryBean ehcache) {
//        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManager(ehcache.getObject());
//        return ehCacheManager;
//    }


    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        return new ShiroDbRealm();
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
        return simpleCookie;
    }

    /**
     * Shiro的过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        /**
         * 默认的登陆访问url
         */
        shiroFilter.setLoginUrl("/");
        /**
         * 登陆成功后跳转的url
         */
        shiroFilter.setSuccessUrl("/index");
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
        hashMap.put("/static/**", "anon");
        hashMap.put("/request/user/register", "anon");
        hashMap.put("/release/common/**", "anon");
        hashMap.put("/403", "anon");
        hashMap.put("/404", "anon");
        hashMap.put("/reg", "anon");
        hashMap.put("/login", "anon");
        hashMap.put("/system/login", "anon");
        hashMap.put("/swagger", "anon");
        hashMap.put("/", "anon");
        hashMap.put("/**", "authc");
        hashMap.put("/index", "authc");

        shiroFilter.setFilterChainDefinitionMap(hashMap);

        return shiroFilter;
    }

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager});
        return bean;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor") //依赖其他bean的初始化
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}