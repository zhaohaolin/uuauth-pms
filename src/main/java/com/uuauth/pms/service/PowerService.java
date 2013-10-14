package com.uuauth.pms.service;

import java.util.List;

import com.uuauth.pms.model.Power;

/**
 * TODO
 * 
 * @author qiaofeng
 * @version $Id: PowerService, v 0.1 2012-10-9 下午08:38:08 Exp $
 */
public interface PowerService {
	
	boolean checkPowerIdIsExist(String id);
	
	boolean checkParentPowerIdisExist(String id);
	
	String getNextSubPowerId(String parentId);
	
	boolean addPower(Power power);
	
	List<Power> powerList();
	
	Power getPowerById(String id);
	
	boolean editPower(Power power, String oldId);
	
	boolean delPower(String id);
	
	List<Power> adminPowerList();
	
	List<Power> getAdminPmsList(String role);
	
	String getDomain();
	
}
