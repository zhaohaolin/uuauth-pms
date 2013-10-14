/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    APIService.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:59:02
 */
package com.uuauth.api.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkit.lang.SecurityUtil;
import com.toolkit.lang.StringUtils;
import com.toolkit.lang.WebClient;
import com.uuauth.api.domain.Department;
import com.uuauth.api.domain.Employee;
import com.uuauth.api.domain.Project;
import com.uuauth.api.domain.ProjectRole;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: APIService, v 0.1 2012-5-9 下午02:59:02 Exp $
 */
public class UUAuthAPIService {
	
	private ScheduledExecutorService					exec			= Executors
																				.newSingleThreadScheduledExecutor();
	private static final ConcurrentMap<String, String>	TOKEN_MAP		= new ConcurrentHashMap<String, String>();
	private String										apiUrl			= "http://0.0.0.0:580";
	private String										password;
	private String										token;
	private String										suffix			= "";
	private Logger										logger			= LoggerFactory
																				.getLogger(getClass());
	private Map<String, String>							suffixMap		= new ConcurrentHashMap<String, String>();
	private Map<Integer, Employee>						userMap			= new ConcurrentHashMap<Integer, Employee>();
	private Map<Integer, Department>					departmentMap	= new ConcurrentHashMap<Integer, Department>();
	private Map<String, Map<String, String>>			cacheMap		= new ConcurrentHashMap<String, Map<String, String>>();
	private Project										project;
	
	private final static class UUAuthAPIServiceContainer {
		private final static UUAuthAPIService	instance	= new UUAuthAPIService();
	}
	
	public final static UUAuthAPIService getInstance() {
		return UUAuthAPIServiceContainer.instance;
	}
	
	/**
	 * 当子系统初始化时，从服务中心拉取用户列表数据
	 */
	private long	initial	= 1000L;			// 1秒后开始
	private long	period	= 1000 * 60 * 10L;	// 每10分钟同步一次用户数据
												
	public void init() {
		final String url = getHttp() + "/remoteauth/get_user_list" + "?token="
				+ token + "&check=" + SecurityUtil.MD5(password + token)
				+ "&suffix=" + suffix;
		logger.info("url=[{}]", url);
		
		exec.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				String json = WebClient.retrieveWebContent(url);
				logger.trace("sync employee list json stream=[{}]", json);
				try {
					ObjectMapper mapper = new ObjectMapper();
					Employee[] employees = mapper.readValue(json,
							Employee[].class);
					System.out.println(employees.length);
					for (int i = 0; i < employees.length; i++) {
						userMap.put(employees[i].getId(), employees[i]);
					}
				} catch (JsonParseException e) {
					logger.error("", e);
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			
		}, initial, period, TimeUnit.MILLISECONDS);
		
	}
	
	public void saveSessionToken(String token, String id) {
		TOKEN_MAP.put(token, id);
	}
	
	public String getIdByToken(String token) {
		return TOKEN_MAP.get(token);
	}
	
	public void cacheToken(String name, String token, String role, String id) {
		if (cacheMap == null) {
			cacheMap = new HashMap<String, Map<String, String>>();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		map.put("role", role);
		map.put("id", id);
		cacheMap.put(name, map);
	}
	
	public Project getProj(String token) {
		if (null != this.project)
			return this.project;
		
		try {
			String url = getHttp() + "/remoteauth/get_proj" + "?token=" + token;
			String json = WebClient.retrieveWebContent(url);
			ObjectMapper mapper = new ObjectMapper();
			project = mapper.readValue(json, Project.class);
			return project;
		} catch (JsonParseException e) {
			logger.error("", e);
		} catch (JsonMappingException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}
	
	public Employee getUserById(int eid) {
		if (userMap.isEmpty()) {
			init();
		}
		Employee employee = userMap.get(eid);
		if (null == employee) {
			addUser(String.valueOf(eid));
			employee = userMap.get(eid);
		}
		return employee;
	}
	
	public boolean addUser(String id) {
		String url = getHttp() + "/remoteauth/get_user" + "?id=" + id
				+ "&token=" + token + "&check="
				+ SecurityUtil.MD5(id + password + token);
		String json = WebClient.retrieveWebContent(url);
		try {
			ObjectMapper mapper = new ObjectMapper();
			Employee employee = mapper.readValue(json, Employee.class);
			userMap.put(employee.getId(), employee);
			System.out.println("add User ..... true");
			return true;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (JsonParseException e) {
			logger.error("", e);
		} catch (JsonMappingException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return false;
	}
	
	public Map<String, String> checkToken(String name, String token) {
		Map<String, String> result = null;
		if (cacheMap.containsKey(name)) {
			Map<String, String> map = cacheMap.get(name);
			if (token.equals(map.get("token"))) {
				result = new HashMap<String, String>();
				result.put("role", map.get("role"));
				result.put("id", map.get("id"));
			}
			cacheMap.remove(map);
		}
		return result;
	}
	
	public List<Department> getChildDepartmentList(int parentId) {
		List<Department> list = new ArrayList<Department>();
		if (departmentMap.isEmpty()) {
			getDepartmentList();
		}
		if (departmentMap.isEmpty()) {
			for (Integer id : departmentMap.keySet()) {
				Department department = departmentMap.get(id);
				if (department.getParentId().intValue() == parentId)
					list.add(department);
			}
		}
		return list;
	}
	
	public Map<Integer, Department> getDepartmentList() {
		if (departmentMap.isEmpty()) {
			try {
				String url = getHttp() + "/remoteauth/get_department_list"
						+ "?token=" + token;
				String json = WebClient.retrieveWebContent(url);
				ObjectMapper mapper = new ObjectMapper();
				Department[] departments = mapper.readValue(json,
						Department[].class);
				if (null != departments) {
					for (int i = 0; i < departments.length; i++) {
						departmentMap.put(departments[i].getId(),
								departments[i]);
					}
				}
			} catch (JsonParseException e) {
				logger.error("", e);
			} catch (JsonMappingException e) {
				logger.error("", e);
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return departmentMap;
	}
	
	public List<Employee> getUserList(int status) {
		List<Employee> list = new ArrayList<Employee>();
		if (userMap.isEmpty()) {
			init();
		}
		for (Integer id : userMap.keySet()) {
			Employee employee = userMap.get(id);
			if (status != -1) {
				if (employee.getStatus() != status)
					continue;
			}
			list.add(employee);
		}
		return list;
	}
	
	/**
	 * 根据子部门的Id查询所有本子部门的员工
	 * 
	 * @param childDepId
	 * @return
	 */
	public List<Employee> getUserListByChildDepartmentId(int childDepId) {
		List<Employee> list = new ArrayList<Employee>();
		if (userMap.isEmpty()) {
			init();
		}
		for (Integer id : userMap.keySet()) {
			Employee employee = userMap.get(id);
			if (employee.getDepid().intValue() == childDepId)
				list.add(employee);
		}
		return list;
	}
	
	public String getUserRole(String token, Integer eid) {
		String url = getHttp() + "/remoteauth/get_user_role" + "?token="
				+ token + "&eid=" + eid;
		String role = WebClient.retrieveWebContent(url);
		return role;
	}
	
	public List<ProjectRole> getProjRoleList(String token) {
		String url = getHttp() + "/remoteauth/get_proj_role_list" + "?token="
				+ token;
		String json = WebClient.retrieveWebContent(url);
		ObjectMapper mapper = new ObjectMapper();
		ProjectRole[] projectRoles;
		try {
			projectRoles = mapper.readValue(json, ProjectRole[].class);
			return Arrays.asList(projectRoles);
		} catch (JsonParseException e) {
			logger.error("", e);
		} catch (JsonMappingException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}
	
	public String getLoginToken(String name, String role, String id,
			String check) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(role)
				|| StringUtils.isEmpty(id) || StringUtils.isEmpty(check)) {
			return "param error";
		}
		// 安全鉴权
		if (!check
				.equals(SecurityUtil.MD5(name + role + id + password + token))) {
			return "check error";
		}
		String newcheck = StringUtils.GetRandValue32();
		cacheToken(name, newcheck, role, id);
		return (newcheck);
	}
	
	public String getHttp() {
		return getApiUrl();
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	public String getApiUrl() {
		return this.apiUrl;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public boolean addSuffixToCache(String token, String suffix) {
		suffixMap.put(token, suffix);
		return true;
	}
	
	public String getSuffixFromCache(String token) {
		return suffixMap.get(token);
	}
	
}
