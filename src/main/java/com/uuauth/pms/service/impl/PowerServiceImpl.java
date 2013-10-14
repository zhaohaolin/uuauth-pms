package com.uuauth.pms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uuauth.pms.dao.PowerDAO;
import com.uuauth.pms.dao.RoleDAO;
import com.uuauth.pms.dao.impl.PowerDAOImpl;
import com.uuauth.pms.dao.impl.RoleDAOImpl;
import com.uuauth.pms.model.Power;
import com.uuauth.pms.model.Role;
import com.uuauth.pms.service.PowerService;

public class PowerServiceImpl implements PowerService {
	
	private PowerDAO			powerDAO	= new PowerDAOImpl();
	private RoleDAO				roleDAO		= new RoleDAOImpl();
	private String				domain		= ".globe.com";
	private final static Logger	log			= LoggerFactory
													.getLogger(PowerServiceImpl.class);
	
	public void setPowerDAO(PowerDAO powerDAO) {
		this.powerDAO = powerDAO;
	}
	
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public boolean checkPowerIdIsExist(String id) {
		Power power = powerDAO.getPowerById(id);
		if (null != power) {
			log.debug("power=[{}] is exist by id=[{}]", new Object[] { power,
					id });
			return true;
		}
		
		log.debug("power is not exist by id=[{}]", new Object[] { id });
		return false;
	}
	
	public boolean checkParentPowerIdisExist(String id) {
		if (id.length() == 2) {
			return true;
		}
		id = id.substring(0, id.length() - 2);
		return checkPowerIdIsExist(id);
	}
	
	public String getNextSubPowerId(String parentId) {
		if (parentId.length() >= 6)
			return "";
		int number = powerDAO.getMaxIdByParentId(parentId);
		if (number == 0) {
			return parentId + "01";
		}
		String num = "" + (number + 1);
		if (num.length() % 2 == 0)
			return num;
		return "0" + num;
	}
	
	public boolean addPower(Power power) {
		powerDAO.addPower(power);
		log.debug("add power=[{}] is ok.", power);
		return true;
	}
	
	public List<Power> powerList() {
		List<Power> list = powerDAO.findPowerAll();
		return list;
	}
	
	public Power getPowerById(String id) {
		Power power = powerDAO.getPowerById(id);
		return power;
	}
	
	public boolean editPower(Power power, String oldId) {
		// 只修改普通属性
		if (oldId.equals(power.getId())) {
			powerDAO.updatePower(power);
			return true;
		}
		// 检查修改后的ID是否已经存在
		if (null == powerDAO.checkPowerIdIsExistByNewId(power)) {
			powerDAO.updatePowerBySQL(power);
			// 修改角色中权限分配
			roleDAO.updatePowersByPowerId(power.getOldId(), power.getId());
			return true;
		}
		return false;
	}
	
	public boolean delPower(String powerId) {
		// 删除角色中分配
		roleDAO.deletePowersByPowerId(powerId);
		// 删除权限
		powerDAO.deletePower(powerId);
		return true;
	}
	
	public List<Power> adminPowerList() {
		List<Power> list = new ArrayList<Power>();
		List<Power> dblist = powerDAO.findPowerAll();
		if (null != dblist && !dblist.isEmpty()) {
			for (Power power : dblist) {
				if (power.getId().length() == 2) {
					list.add(power);
				} else if (power.getId().length() == 4) {
					List<Power> list1 = list.get(list.size() - 1).getList();
					list1.add(power);
				} else if (power.getId().length() == 6) {
					List<Power> list1 = list.get(list.size() - 1).getList();
					List<Power> list2 = list1.get(list1.size() - 1).getList();
					list2.add(power);
				}
			}
		}
		return list;
	}
	
	public List<Power> getAdminPmsList(String roles) {
		List<Power> list = new ArrayList<Power>();
		String pmsIdList = "";
		List<Role> roleList = roleDAO.getSysRoleList(roles);
		if (null == roleList || roleList.isEmpty()) {
			log.warn("role list is null or empty by roles=[{}]", roles);
			return list;
		}
		
		for (Role role : roleList) {
			pmsIdList += role.getPowers();
		}
		log.info("pms id list=[{}]", pmsIdList);
		
		list = powerDAO.getSysPowerList(pmsIdList);
		return list;
	}
	
	@Override
	public String getDomain() {
		return this.domain;
	}
	
}
