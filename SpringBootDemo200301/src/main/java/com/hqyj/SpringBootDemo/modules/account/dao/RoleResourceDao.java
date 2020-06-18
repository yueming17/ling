package com.hqyj.SpringBootDemo.modules.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleResourceDao {

	@Delete("delete from role_resource where resource_id = #{resourceId}")
	void deletRoleResourceByResourceId(int resourceId);
	
	@Insert("insert role_resource(role_id, resource_id) value(#{roleId}, #{resourceId})")
	void insertRoleResource(@Param("roleId") int roleId, @Param("resourceId") int resourceId);
	
}
