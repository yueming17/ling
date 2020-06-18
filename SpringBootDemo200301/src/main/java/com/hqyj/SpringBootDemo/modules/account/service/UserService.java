package com.hqyj.SpringBootDemo.modules.account.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.account.entity.User;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;

public interface UserService {
	User getUserByUserName(String userName);

	Result<User> login(User user);

	PageInfo<User> getUsersBySearchVo(SearchVo searchVo);
	
	User getUserByUserId(int userId);
	
	Result<Object> deleteUserByUserId(int userId);
	
	Result<User> edit(User user);
	
	Result<String> updateUserFile(MultipartFile userImage);

	Result<User> editProfile(User user);
}
