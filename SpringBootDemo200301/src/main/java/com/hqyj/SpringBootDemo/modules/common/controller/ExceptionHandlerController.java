package com.hqyj.SpringBootDemo.modules.common.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.Result.ResultStatus;

@ControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler(value = AuthorizationException.class)
	@ResponseBody
	public Result<String> exceptionHandlerFor304() {
		return new Result<String>(ResultStatus.FAILD.status, "You have no permission.", "/common/403");
	}
}