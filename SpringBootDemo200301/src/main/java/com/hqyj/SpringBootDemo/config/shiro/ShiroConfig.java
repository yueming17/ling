package com.hqyj.SpringBootDemo.config.shiro;


import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	@Autowired
	private MyRealm myRealm;

//安全管理器組件
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm);
		return securityManager;
	}
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean FilterFactoryBean = new ShiroFilterFactoryBean();
		FilterFactoryBean.setSecurityManager(securityManager());
		FilterFactoryBean.setLoginUrl("/account/login");
		FilterFactoryBean.setSuccessUrl("/common/dashboard");
		

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("/static/**", "anon");
		map.put("/custom/**", "anon");
		map.put("/images/**", "anon");
		map.put("/shopping/**", "anon");
		map.put("/vendors/**", "anon");
		map.put("/account/login", "anon");
		map.put("/account/register", "anon");
		map.put("/api/login", "anon");
		map.put("/api/user", "anon");
		map.put("/test/**", "anon");

		// 如果使用“记住我功能”，则采用user规则，如果必须要用户登录，则采用authc规则
		map.put("/**", "authc");
//		map.put("/pay/**", "authc");
		FilterFactoryBean.setFilterChainDefinitionMap(map);
		return FilterFactoryBean;
	}
	
	/**
	 * --注册shiro方言，让thymeleaf支持shiro标签
	 */
	@Bean
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}

	/**
	 * --自动代理类，支持Shiro的注解
	 */
	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

    /**
     * --开启Shiro的注解
     */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}
}
