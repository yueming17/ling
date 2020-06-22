package com.hqyj.SpringBootDemo.modules.account.controller;


import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.account.entity.User;
import com.hqyj.SpringBootDemo.modules.account.service.UserService;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 127.0.0.1/api/user
	 */
	@PostMapping(value = "/user", consumes = "application/json")
	@RequiresRoles(value={"admin","boss"}, logical=Logical.AND)
	public Result<User> insertUser(@RequestBody User user) {
		return userService.edit(user);
	}

	@PostMapping(value = "/login", consumes = "application/json")
	public Result<User> login(@RequestBody User user) {
		return userService.login(user);
	}

	@PostMapping(value = "/users", consumes = "application/json")
	public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo) {
		return userService.getUsersBySearchVo(searchVo);
	}

	/**
	 * 127.0.0.1/api/user/3
	 */
	@RequestMapping("/user/{userId}")
	public User getUserByUserId(@PathVariable int userId) {
		return userService.getUserByUserId(userId);
	}
	
	/**
	 * 127.0.0.1/api/user
	 */
	@DeleteMapping("/user/{userId}")
	@RequiresPermissions(value={"/api/user","insert"}, logical= Logical.OR)
	public Result<Object> deleteUserByUserId(@PathVariable int userId) {
		return userService.deleteUserByUserId(userId);
	}
	
	@PutMapping(value = "/user",consumes = "application/json")
	public Result<User> updateUser(@RequestBody User user){
		return userService.edit(user);
	}
	
	@PostMapping(value = "userImage",consumes = "multipart/form-data")
	public Result<String> updateUserFile(@RequestBody MultipartFile userImage) {
		return userService.updateUserFile(userImage);
	}
	
	@PutMapping(value = "/profile",consumes = "application/json")
	public Result<User> updateProfile(@RequestBody User user){
		return userService.editProfile(user);
	}
}
