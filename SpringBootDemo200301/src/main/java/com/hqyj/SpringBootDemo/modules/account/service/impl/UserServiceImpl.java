package com.hqyj.SpringBootDemo.modules.account.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.config.ResourceConfigBean;
import com.hqyj.SpringBootDemo.modules.account.dao.UserDao;
import com.hqyj.SpringBootDemo.modules.account.dao.UserRoleDao;
import com.hqyj.SpringBootDemo.modules.account.entity.Role;
import com.hqyj.SpringBootDemo.modules.account.entity.User;
import com.hqyj.SpringBootDemo.modules.account.service.UserService;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.Result.ResultStatus;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;
import com.hqyj.SpringBootDemo.utils.FileUtil;
import com.hqyj.SpringBootDemo.utils.MD5Util;

@Service
public class UserServiceImpl implements UserService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private ResourceConfigBean resourceConfigBean;

	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public Result<User> login(User user) {
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if (userTemp == null || !userTemp.getPassword().equals(MD5Util.getMD5(user.getPassword()))) {
			return new Result<User>(ResultStatus.FAILD.status, "User name or password error.");
		} else {
			return new Result<User>(ResultStatus.SUCCESS.status, "Login success.", userTemp);
		}
	}

	@Override
	public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		return new PageInfo<>(
				Optional.ofNullable(userDao.getUsersBySearchVo(searchVo)).orElse(Collections.emptyList()));
	}

	@Override
	public User getUserByUserId(int userId) {
		return userDao.getUserByUserId(userId);
	}

	@Override
	@Transactional
	public Result<User> edit(User user) {
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
			return new Result<User>(ResultStatus.FAILD.status, "User name is repeat.");
		}
		if (user.getUserId() > 0) {
			userDao.updateUser(user);
			userRoleDao.deleteRolesByUserId(user.getUserId());
		} else {
			user.setPassword(MD5Util.getMD5(user.getPassword()));
			user.setCreateDate(new Date());
			userDao.insertUser(user);
		}
		List<Role> roles = user.getRoles();
		if (roles != null && roles.size() > 0) {
			for (Role role : roles) {
				userRoleDao.insertUserRole(user.getUserId(), role.getRoleId());
			}
		}
		return new Result<User>(ResultStatus.SUCCESS.status, "edit success.", user);
	}

	@Override
	public Result<Object> deleteUserByUserId(int userId) {
		userDao.deleteUserByUserId(userId);
		userRoleDao.deleteRolesByUserId(userId);
		return new Result<Object>(ResultStatus.SUCCESS.status, "Delete success.");
	}

	@Override
	public Result<String> updateUserFile(MultipartFile userImage) {
		if (userImage.isEmpty()) {
			return new Result<String>(ResultStatus.SUCCESS.status, "file upload fialed.");
		}
		//判断上传的文件类型
		if (!FileUtil.isImage(userImage)) {
			return new Result<String>(ResultStatus.SUCCESS.status, "File is not a image");
		}
		String originalFilename = userImage.getOriginalFilename();
		//相对路径
		String relatedPath = resourceConfigBean.getResourcePath()+originalFilename;
		//本地路径
		String localPath = String.format("%s%s",resourceConfigBean.getLocalPathForWindow(), originalFilename);
		
		File destFile = new File(localPath);
		try {
			userImage.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
			return new Result<>(ResultStatus.FAILD.status, "File upload error.");
		}
		return new Result<>(ResultStatus.SUCCESS.status, "File upload success.", relatedPath);
	}

	@Override
	@Transactional
	public Result<User> editProfile(User user) {
		User userTemp = getUserByUserName(user.getUserName());
		if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
			return new Result<User>(ResultStatus.FAILD.status, "User name is repeat.");
		}
		userDao.editProfile(user);

		return new Result<User>(ResultStatus.SUCCESS.status, "Edit success.", user);
	}

}