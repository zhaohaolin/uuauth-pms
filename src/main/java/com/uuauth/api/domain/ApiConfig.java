/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ApiConfig.java
 * Creator:     qiaofeng
 * Create-Date: 下午08:42:14
 */
package com.uuauth.api.domain;

/**
 * api config bean for spring config bean
 * 
 * @author qiaofeng
 * @version $Id: ApiConfig, v 0.1 2012-10-9 下午08:42:14 Exp $
 */
public class ApiConfig implements IApiConfig {
	
	private String	token		= "";
	private String	password	= "";
	private String	apiUrl		= "";
	private String	suffix		= "";
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getApiUrl() {
		return apiUrl;
	}
	
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}
