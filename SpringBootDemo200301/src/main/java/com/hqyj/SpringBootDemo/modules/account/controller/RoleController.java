package com.hqyj.SpringBootDemo.modules.account.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.account.entity.Role;
import com.hqyj.SpringBootDemo.modules.account.entity.User;
import com.hqyj.SpringBootDemo.modules.account.service.RoleService;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;

@RestController
@RequestMapping("/api")
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 127.0.0.1/api/roles
	 */
	@RequestMapping("/roles")
	List<Role> getRoles() {
		return roleService.getRoles();
	}

	@PostMapping(value = "/roles", consumes = "application/json")
	public PageInfo<Role> getRolesBySearchVo(@RequestBody SearchVo searchVo) {
		return roleService.getRolesBySearchVo(searchVo);
	}

	@PostMapping(value = "/role", consumes = "application/json")
	public Result<Role> insertUser(@RequestBody Role role) {
		return roleService.edit(role);
	}

	/**
	 * 127.0.0.1/api/role/3
	 */
	@RequestMapping("/role/{roleId}")
	public Role getUserByUserId(@PathVariable int roleId) {
		return roleService.getRoleByRoleId(roleId);
	}

	/**
	 * 127.0.0.1/api/role
	 */
	@PutMapping(value = "/role", consumes = "application/json")
	public Result<Role> updateRole(@RequestBody Role role) {
		return roleService.edit(role);
	}

	/**
	 * 127.0.0.1/api/role/4
	 */
	@DeleteMapping("/role/{roleId}")
	public Result<Object> deleteRole(@PathVariable int roleId) {
		return roleService.deleteRole(roleId);
	}
}
