package com.hqyj.SpringBootDemo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hqyj.SpringBootDemo.modules.account.entity.Role;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;

@Mapper
public interface RoleDao {

	@Select("select * from role")
	List<Role> getRoles();
	
	@Select("select * from role role left join user_role userRole "
			+ "on role.role_id = userRole.role_id where userRole.user_id = #{userId}")
	List<Role> getRolesByUserId(int userId);
	
	@Select("<script>" + 
			"select * from role "
			+ "<where> "
			+ "<if test='keyWord != \"\" and keyWord != null'>"
			+ " and (role_name like '%${keyWord}%') "
			+ "</if>"
			+ "</where>"
			+ "<choose>"
			+ "<when test='orderBy != \"\" and orderBy != null'>"
			+ " order by ${orderBy} ${sort}"
			+ "</when>"
			+ "<otherwise>"
			+ " order by role_id desc"
			+ "</otherwise>"
			+ "</choose>"
			+ "</script>")
/* ++++++++++++++++++++++++++role表的增刪改查++++++++++++++++++++++++++++ */
	List<Role> getRolesBySearchVo(SearchVo searchVo);
	
	@Insert("insert into role (role_name) values (#{roleName})")
	@Options(useGeneratedKeys = true, keyColumn = "role_id", keyProperty = "roleId")
	void insertRole(Role role);
	
	@Select("select * from role where role_name = #{roleName} limit 1")
	Role getRoleByRoleName(String roleName);
	
	@Select("select * from role where role_id=#{roleId}")
	Role getRoleByRoleId(int roleId);
	
	@Update("update role set role_name = #{roleName} where role_id = #{roleId}")
	void updateRole(Role role);
	
	@Delete("delete from role where role_id = #{roleId}")
	void deleteRole(int roleId);
	
	@Select("select * from role role left join role_resource roleResource "
			+ "on role.role_id = roleResource.role_id where roleResource.resource_id = #{resourceId}")
	List<Role> getRolesByResourceId(int resourceId);
}
