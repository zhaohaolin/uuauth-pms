package com.uuauth.pms.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uuauth.pms.dao.PowerDAO;
import com.uuauth.pms.dao.SqliteUtils;
import com.uuauth.pms.model.Power;

public class PowerDAOImpl implements PowerDAO {
	
	private final static Logger			LOG			= LoggerFactory
															.getLogger(PowerDAOImpl.class);
	private final static PowerDAOImpl	instance	= new PowerDAOImpl();
	private SqliteUtils					DB			= SqliteUtils.getInstance();
	
	public static final PowerDAOImpl getInstance() {
		return instance;
	}
	
	private PowerDAOImpl() {
		// 检查是否创建表，如果没有创建要新建表
		String sql = "CREATE TABLE `t_pms_powers` (";
		sql += "`id` varchar(16) NOT NULL,";
		sql += "`name` varchar(32) NOT NULL,";
		sql += "`power` text,";
		sql += "`url` varchar(64) DEFAULT NULL,";
		sql += "`description` varchar(128) DEFAULT NULL,";
		sql += "`menu` tinyint(1) DEFAULT NULL,";
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
	
	public void addPower(Power power) {
		String sql = "INSERT INTO t_pms_powers VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, power.getId());
			state.setString(2, power.getName());
			state.setString(3, power.getPower());
			state.setString(4, power.getUrl());
			state.setBoolean(5, power.isMenu());
			state.setString(6, power.getDescription());
			state.execute();
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public Power getPowerById(String id) {
		String sql = "SELECT id, name, power, url, menu, description FROM t_pms_powers ORDER BY id ASC LIMIT 0,1";
		try {
			Statement state = DB.getConnection().createStatement();
			ResultSet set = state.executeQuery(sql);
			state.close();
			Power power = null;
			while (set.next()) {
				power = new Power();
				power.setId(set.getString("id"));
				power.setName(set.getString("name"));
				power.setPower(set.getString("power"));
				power.setUrl(set.getString("url"));
				power.setMenu(set.getBoolean("menu"));
				power.setDescription(set.getString("description"));
				break;
			}
			return power;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updatePower(Power power) {
		String sql = "UPDATE t_pms_powers SET NAME=?, POWER=?, URL=?, MENU=?, DESCRIPTION=? WHERE ID LIKE ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, power.getName());
			state.setString(2, power.getPower());
			state.setString(3, power.getUrl());
			state.setBoolean(4, power.isMenu());
			state.setString(5, power.getDescription());
			state.setString(6, power.getId());
			state.execute();
			state.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePowerBySQL(Power power) {
		String sql = "UPDATE t_pms_powers SET ID=CONCAT(#id#,SUBSTRING(ID,#oldLength#)) WHERE ID LIKE #oldId#";
		System.out.println(sql);
	}
	
	public void deletePower(String powerId) {
		String sql = "DELETE FROM t_pms_powers WHERE ID LIKE ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, powerId + "%");
			state.execute();
			state.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getMaxIdByParentId(String parentId) {
		String sql = "SELECT MAX(ID) AS MAXID FROM t_pms_powers WHERE ID LIKE "
				+ parentId + "__";
		try {
			Statement state = DB.getConnection().createStatement();
			ResultSet set = state.executeQuery(sql);
			state.close();
			while (set.next()) {
				return set.getInt("MAXID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public Power checkPowerIdIsExistByNewId(Power power) {
		String sql = "SELECT id, name, power, url, menu, description FROM t_pms_powers WHERE ID <> ? AND ID = ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, power.getOldId());
			state.setString(2, power.getId());
			ResultSet set = state.executeQuery();
			state.close();
			while (set.next()) {
				power = new Power();
				power.setId(set.getString("id"));
				power.setName(set.getString("name"));
				power.setPower(set.getString("power"));
				power.setUrl(set.getString("url"));
				power.setMenu(set.getBoolean("menu"));
				power.setDescription(set.getString("description"));
				break;
			}
			return power;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Power checkPowerIdIsExist(String id) {
		String sql = "SELECT id, name, power, url, menu, description FROM t_pms_powers WHERE ID <> ?";
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, id);
			ResultSet set = state.executeQuery();
			state.close();
			while (set.next()) {
				Power power = new Power();
				power.setId(set.getString("id"));
				power.setName(set.getString("name"));
				power.setPower(set.getString("power"));
				power.setUrl(set.getString("url"));
				power.setMenu(set.getBoolean("menu"));
				power.setDescription(set.getString("description"));
				return power;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Power> findPowerAll() {
		String sql = "SELECT id, name, power, url, menu, description FROM t_pms_powers ORDER BY id ASC";
		List<Power> list = new ArrayList<Power>();
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			ResultSet set = state.executeQuery();
			state.close();
			while (set.next()) {
				Power power = new Power();
				power.setId(set.getString("id"));
				power.setName(set.getString("name"));
				power.setPower(set.getString("power"));
				power.setUrl(set.getString("url"));
				power.setMenu(set.getBoolean("menu"));
				power.setDescription(set.getString("description"));
				list.add(power);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Power> getSysPowerList(String powerIds) {
		String sql = "SELECT id, name, power, url, menu, description FROM t_pms_powers WHERE ? LIKE CONCAT('%|', id, '|%') ORDER BY id";
		List<Power> list = new ArrayList<Power>();
		try {
			PreparedStatement state = DB.getConnection().prepareStatement(sql);
			state.setString(1, powerIds);
			ResultSet set = state.executeQuery();
			state.close();
			while (set.next()) {
				Power power = new Power();
				power.setId(set.getString("id"));
				power.setName(set.getString("name"));
				power.setPower(set.getString("power"));
				power.setUrl(set.getString("url"));
				power.setMenu(set.getBoolean("menu"));
				power.setDescription(set.getString("description"));
				list.add(power);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
