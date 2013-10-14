/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    RoleAssembler.java
 * Creator:     joe.zhao
 * Create-Date: 下午08:33:38
 */
package com.uuauth.pms.controller;

import java.util.ArrayList;
import java.util.List;

import com.uuauth.api.domain.ProjectRole;
import com.uuauth.pms.model.Role;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: RoleAssembler, v 0.1 2012-5-20 下午08:33:38 Exp $
 */
public abstract class RoleAssembler {
	
	public final static List<Role> projRoleListToRoleList(List<ProjectRole> list) {
		List<Role> rlist = new ArrayList<Role>();
		if (null == list || list.isEmpty())
			return rlist;
		for (ProjectRole projectRole : list) {
			Role role = new Role();
			role.setId(projectRole.getId());
			role.setName(projectRole.getRoleName());
			role.setDescription(projectRole.getRoleDesc());
			rlist.add(role);
		}
		return rlist;
	}
	
}
