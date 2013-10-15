package com.uuauth.pms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uuauth.pms.dao.RoleDAO;
import com.uuauth.pms.dao.impl.RoleDAOImpl;
import com.uuauth.pms.model.Role;
import com.uuauth.pms.service.RoleService;

public class RoleServiceImpl implements RoleService {
	
	private final static Logger				LOG			= LoggerFactory
																.getLogger(RoleServiceImpl.class);
	private RoleDAO							roleDAO		= RoleDAOImpl
																.getInstance();
	private final static RoleServiceImpl	instance	= new RoleServiceImpl();
	
	private RoleServiceImpl() {
		//
	}
	
	public static final RoleServiceImpl getInstance() {
		return instance;
	}
	
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	public List<String> getRoleList() {
		List<String> list = new ArrayList<String>();
		List<Role> list2 = roleDAO.getAllRoleList();
		if (null != list2 && list2.size() > 0) {
			for (Role role : list2) {
				list.add(role.getName());
			}
		}
		return list;
	}
	
	public boolean addRole(Role role) {
		roleDAO.addRole(role);
		return true;
	}
	
	public Map<String, Role> getRoleMap() {
		Map<String, Role> map = new HashMap<String, Role>();
		List<Role> list = roleDAO.getAllRoleList();
		if (null != list && list.size() > 0) {
			for (Role role : list) {
				map.put(role.getName(), role);
			}
		}
		return map;
	}
	
	public Role getRoleByName(String name) {
		return roleDAO.getRoleByName(name);
	}
	
	public boolean editRole(Role role) {
		roleDAO.updateRole(role);
		return true;
	}
	
	public boolean delRole(String roleName) {
		return true;
	}
	
	public boolean updatePowers(String oldPower, String newPower) {
		// 先把这个权限的所有角色列表查询出来
		LOG.info("");
		return false;
	}
	
}
