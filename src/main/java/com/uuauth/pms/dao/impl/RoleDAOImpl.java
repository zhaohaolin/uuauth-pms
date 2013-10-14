package com.uuauth.pms.dao.impl;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.uuauth.pms.dao.RoleDAO;
import com.uuauth.pms.model.Role;

public class RoleDAOImpl implements RoleDAO {
	
	public void addRole(Role role) {
		Ebean.save(role);
	}
	
	public Role getRoleById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateRole(Role role) {
		Ebean.update(role);
	}
	
	public void updateRolePowers(Role role) {
		String sql = "UPDATE t_pms_role SET powers = :powers WHERE id = :id";
		Ebean.createUpdate(Role.class, sql).set("powers", role.getPowers())
				.set("id", role.getId()).execute();
	}
	
	public void deleteRole(int id) {
		Ebean.delete(Role.class, id);
	}
	
	public List<Role> getAllRoleList() {
		return Ebean.find(Role.class).findList();
	}
	
	public List<Role> findRoleListByRoelIds(String roleIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String findPowerIdsByRoleIds(String roleIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Role getRoleByName(String name) {
		return Ebean.find(Role.class).where().eq("name", name).findUnique();
	}
	
	public boolean checkRoleIsExistByNameNotId(Role role) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean deletePowersByPowerId(String powerId) {
		String sql = "UPDATE t_pms_role SET powers=REPLACE(powers, :powerId, '')";
		Ebean.createUpdate(Role.class, sql).set("powerId", powerId).execute();
		return true;
	}
	
	public boolean updatePowersByPowerId(String oldId, String newId) {
		String sql = "UPDATE t_pms_role SET powers=REPLACE(powers, :oldId, :newId)";
		Ebean.createUpdate(Role.class, sql).set("oldId", oldId)
				.set("newId", newId).execute();
		return true;
	}
	
	public boolean addPowerIds(Role role) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Role> getSysRoleList(String roles) {
		String sql = "SELECT id, name, description, powers FROM t_pms_role WHERE :roles Like concat('%|', name , '|%')";
		return Ebean.createQuery(Role.class, sql).setParameter("roles", roles)
				.findList();
	}
	
}
