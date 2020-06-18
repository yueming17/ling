package com.hqyj.SpringBootDemo.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {
	@RequestMapping("/dashboard")
	public String dashboardpage() {
		return "index";
	}
}
