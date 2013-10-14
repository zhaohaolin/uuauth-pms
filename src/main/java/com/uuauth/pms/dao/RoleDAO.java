package com.uuauth.pms.dao;

import java.util.List;

import com.uuauth.pms.model.Role;

public interface RoleDAO {
	
	void addRole(Role role);
	
	Role getRoleById(int id);
	
	void updateRole(Role role);
	
	void updateRolePowers(Role role);
	
	void deleteRole(int id);
	
	List<Role> getAllRoleList();
	
	List<Role> getSysRoleList(String roles);
	
	List<Role> findRoleListByRoelIds(String roleIds);
	
	String findPowerIdsByRoleIds(String roleIds);
	
	Role getRoleByName(String name);
	
	boolean checkRoleIsExistByNameNotId(Role role);
	
	boolean deletePowersByPowerId(String powerId);
	
	boolean updatePowersByPowerId(String oldId, String newId);
	
	boolean addPowerIds(Role role);
	
}
