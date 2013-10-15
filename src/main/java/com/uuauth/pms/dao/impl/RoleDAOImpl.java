package com.uuauth.pms.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uuauth.pms.dao.RoleDAO;
import com.uuauth.pms.dao.SqliteUtils;
import com.uuauth.pms.model.Role;

public class RoleDAOImpl implements RoleDAO {
	
	private final static Logger			LOG			= LoggerFactory
															.getLogger(RoleDAOImpl.class);
	private final static RoleDAOImpl	instance	= new RoleDAOImpl();
	private final SqliteUtils			DB			= SqliteUtils.getInstance();
	
	private RoleDAOImpl() {
		// 检查是否创建表，如果没有创建要新建表
		String sql = "CREATE TABLE `t_pms_role` (";
		sql += "_id primary key autoincrement,";
		sql += "`name` varchar(32) NOT NULL,";
		sql += "`description` varchar(128) NOT NULL,";
		sql += "`powers` varchar(255) NOT NULL,";
		sql += "PRIMARY KEY (`id`)";
		sql += ");";
		
		try {
			Statement state = DB.getConnection().createStatement();
			state.executeUpdate(sql);
			state.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			LOG.info("表已经存在了.");
		}
	}
	
	public static final RoleDAOImpl getInstance() {
		return instance;
	}
	
	public void addRole(Role role) {
		String sql = "INSERT INTO t_pms_role VALUES (NULL, #name#, #description#, #powers#)";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, role.getName());
			state.setString(2, role.getDescription());
			state.setString(3, role.getPowers());
			state.execute();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Role getRoleById(int id) {
		String sql = "SELECT _id, name, description ,powers FROM t_pms_role WHERE id = ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setInt(1, id);
			ResultSet set = state.executeQuery();
			state.close();
			Role role = null;
			while (set.next()) {
				role = new Role();
				role.setId(set.getInt("_id"));
				role.setName(set.getString("name"));
				role.setDescription(set.getString("description"));
				role.setPowers(set.getString("powers"));
				break;
			}
			return role;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateRole(Role role) {
		String sql = "UPDATE t_pms_role SET description=?, powers = ? WHERE name=?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, role.getDescription());
			state.setString(2, role.getPowers());
			state.setString(3, role.getName());
			state.execute();
			state.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateRolePowers(Role role) {
		String sql = "UPDATE t_pms_role SET powers = ? WHERE _id = ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, role.getPowers());
			state.setInt(2, role.getId());
			state.execute();
			state.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRole(int id) {
		String sql = "DELETE FROM t_pms_role WHERE _id = ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setInt(1, id);
			state.execute();
			state.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Role> getAllRoleList() {
		String sql = "SELECT _id, name, description ,powers FROM t_pms_role";
		List<Role> list = new ArrayList<Role>();
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			ResultSet set = state.executeQuery();
			state.close();
			while (set.next()) {
				Role role = new Role();
				role.setId(set.getInt("_id"));
				role.setName(set.getString("name"));
				role.setPowers(set.getString("powers"));
				role.setDescription(set.getString("description"));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
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
		String sql = "SELECT _id, name, description ,powers FROM t_pms_role WHERE name = ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, name);
			ResultSet set = state.executeQuery();
			state.close();
			while (set.next()) {
				Role role = new Role();
				role.setId(set.getInt("_id"));
				role.setName(set.getString("name"));
				role.setPowers(set.getString("powers"));
				role.setDescription(set.getString("description"));
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean checkRoleIsExistByNameNotId(Role role) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean deletePowersByPowerId(String powerId) {
		String sql = "UPDATE t_pms_role SET powers=REPLACE(powers, ?, '')";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, powerId);
			state.execute();
			state.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean updatePowersByPowerId(String oldId, String newId) {
		String sql = "UPDATE t_pms_role SET powers=REPLACE(powers, ?, ?)";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, oldId);
			state.setString(2, newId);
			state.execute();
			state.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean addPowerIds(Role role) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Role> getSysRoleList(String roles) {
		String sql = "SELECT _id, name, description, powers FROM t_pms_role WHERE ? Like concat('%|', name , '|%')";
		List<Role> list = new ArrayList<Role>();
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, roles);
			ResultSet set = state.executeQuery();
			state.close();
			while (set.next()) {
				Role role = new Role();
				role.setId(set.getInt("_id"));
				role.setName(set.getString("name"));
				role.setPowers(set.getString("powers"));
				role.setDescription(set.getString("description"));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
