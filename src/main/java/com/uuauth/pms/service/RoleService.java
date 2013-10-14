package com.uuauth.pms.service;

import java.util.List;
import java.util.Map;

import com.uuauth.pms.model.Role;

/**
 * RoleService 角色服务类
 * 
 * @author qiaofeng
 * @version $Id: RoleService, v 0.1 2012-10-9 下午08:37:41 Exp $
 */
public interface RoleService {
	
	List<String> getRoleList();
	
	boolean addRole(Role role);
	
	Map<String, Role> getRoleMap();
	
	Role getRoleByName(String name);
	
	boolean editRole(Role role);
	
	boolean delRole(String roleName);
	
	boolean updatePowers(String oldPower, String newPower);
	
}
