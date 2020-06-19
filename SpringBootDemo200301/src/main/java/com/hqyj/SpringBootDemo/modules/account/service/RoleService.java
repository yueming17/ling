package com.hqyj.SpringBootDemo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.account.entity.Role;
import com.hqyj.SpringBootDemo.modules.account.entity.User;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;

public interface RoleService {

	List<Role> getRoles();
	
	PageInfo<Role> getRolesBySearchVo(SearchVo searchVo);
	
	Role getRoleByRoleId(int roleId);
	
	Result<Role> edit(Role role);
	
	Result<Object> deleteRole(int roleId);
	
	List<Role> getRolesByUserId(int userId);
}
