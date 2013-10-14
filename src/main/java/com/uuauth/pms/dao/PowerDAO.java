package com.uuauth.pms.dao;

import java.util.List;

import com.uuauth.pms.model.Power;

public interface PowerDAO {
	
	void addPower(Power power);
	
	Power getPowerById(String id);
	
	void updatePower(Power power);
	
	void updatePowerBySQL(Power power);
	
	void deletePower(String powerId);
	
	int getMaxIdByParentId(String parentId);
	
	Power checkPowerIdIsExistByNewId(Power power);
	
	Power checkPowerIdIsExist(String id);
	
	List<Power> findPowerAll();
	
	List<Power> getSysPowerList(String powerIds);
	
}
