package com.hqyj.SpringBootDemo.modules.account.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.account.dao.RoleDao;
import com.hqyj.SpringBootDemo.modules.account.dao.UserRoleDao;
import com.hqyj.SpringBootDemo.modules.account.entity.Role;
import com.hqyj.SpringBootDemo.modules.account.entity.User;
import com.hqyj.SpringBootDemo.modules.account.service.RoleService;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.Result.ResultStatus;
import com.hqyj.SpringBootDemo.utils.MD5Util;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> getRoles() {
		return Optional.ofNullable(roleDao.getRoles()).orElse(Collections.emptyList());
	}

	@Override
	public PageInfo<Role> getRolesBySearchVo(SearchVo searchVo) {
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		return new PageInfo<>(
				Optional.ofNullable(roleDao.getRolesBySearchVo(searchVo)).orElse(Collections.emptyList()));
	}

	@Override
	public Role getRoleByRoleId(int roleId) {
		return roleDao.getRoleByRoleId(roleId);
	}

	@Override
	@Transactional
	public Result<Role> edit(Role role) {
		Role userTemp = roleDao.getRoleByRoleName(role.getRoleName());
		if (userTemp != null && userTemp.getRoleId() != role.getRoleId()) {
			return new Result<Role >(ResultStatus.FAILD.status, "role name is repeat.");
		}
		if (role.getRoleId() > 0) {
			roleDao.updateRole(role);
		} else {
			roleDao.insertRole(role);
		}
		return new Result<Role>(ResultStatus.SUCCESS.status, "edit success.", role);
	}


	@Override
	public Result<Object> deleteRole(int roleId) {
		roleDao.deleteRole(roleId);
		return new Result<Object>(ResultStatus.SUCCESS.status, "delete success.");
	}

	@Override
	public List<Role> getRolesByUserId(int userId) {
		return roleDao.getRolesByUserId(userId);
	}
	
	
}
