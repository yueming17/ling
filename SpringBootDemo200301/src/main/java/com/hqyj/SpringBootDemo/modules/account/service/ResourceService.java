package com.hqyj.SpringBootDemo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.account.entity.Resource;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;

public interface ResourceService {
	PageInfo<Resource> getResourcesBySearchVo(SearchVo searchVo);
	
	Result<Resource> editResource(Resource resource);

	Resource getResourceById(int resourceId);
	
	Result<Object> deleteResource(int resourceId);
}
