package com.hqyj.SpringBootDemo.config.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hqyj.SpringBootDemo.modules.account.entity.Resource;
import com.hqyj.SpringBootDemo.modules.account.entity.Role;
import com.hqyj.SpringBootDemo.modules.account.entity.User;
import com.hqyj.SpringBootDemo.modules.account.service.ResourceService;
import com.hqyj.SpringBootDemo.modules.account.service.RoleService;
import com.hqyj.SpringBootDemo.modules.account.service.UserService;

@Component
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		User user = userService.getUserByUserName(userName);
		if (user == null) {
			throw new UnknownAccountException("This user name do not exist.");
		}

		// 身份验证器，包装用户名和密码
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo simpleInfo = new SimpleAuthorizationInfo();

		User user = (User) principals.getPrimaryPrincipal();
		if (user == null) {
			throw new UnknownAccountException("This user name do not exist.");
		}
		List<Role> roles = roleService.getRolesByUserId(user.getUserId());
		for (Role role : roles) {
			simpleInfo.addRole(role.getRoleName());
			List<Resource> resources = resourceService.getResourcesByRoleId(role.getRoleId());
			for (Resource resource : resources) {
				simpleInfo.addStringPermission(resource.getPermission());
			}
		}
		return simpleInfo;
	}

}