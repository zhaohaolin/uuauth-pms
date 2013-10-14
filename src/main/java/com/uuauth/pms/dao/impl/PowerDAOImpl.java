package com.uuauth.pms.dao.impl;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.Update;
import com.uuauth.pms.dao.PowerDAO;
import com.uuauth.pms.model.Power;

public class PowerDAOImpl implements PowerDAO {
	
	public void addPower(Power power) {
		Ebean.save(power);
	}
	
	public Power getPowerById(String id) {
		return Ebean.find(Power.class, id);
	}
	
	public void updatePower(Power power) {
		Ebean.update(power);
	}
	
	public void updatePowerBySQL(Power power) {
		// TODO Auto-generated method stub
		
	}
	
	public void deletePower(String powerId) {
		String sql = "DELETE FROM t_pms_powers WHERE ID LIKE :id";
		Update<Power> update = Ebean.createUpdate(Power.class, sql);
		update.set("id", powerId + "%");
		update.execute();
	}
	
	public int getMaxIdByParentId(String parentId) {
		String sql = "SELECT MAX(ID) AS MAXID FROM pms_powers WHERE ID LIKE "
				+ parentId + "__";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		SqlRow sqlRow = sqlQuery.findUnique();
		if (null == sqlRow)
			return 0;
		
		Integer max = sqlRow.getInteger("MAXID");
		if (max == null)
			max = 0;
		return max;
	}
	
	public Power checkPowerIdIsExistByNewId(Power power) {
		return Ebean.find(Power.class).where().eq("name", power.getName())
				.eq("description", power.getDescription()).findUnique();
	}
	
	public Power checkPowerIdIsExist(String id) {
		return Ebean.find(Power.class).where().ne("id", id).findUnique();
	}
	
	public List<Power> findPowerAll() {
		return Ebean.find(Power.class).where().order().asc("id").findList();
	}
	
	public List<Power> getSysPowerList(String powerIds) {
		String sql = "SELECT id, name, power, url, menu, description FROM t_pms_powers WHERE :value LIKE CONCAT('%|', id, '|%') ORDER BY id";
		Query<Power> query = Ebean.createQuery(Power.class, sql);
		query.setParameter("value", powerIds);
		return query.findList();
	}
}
