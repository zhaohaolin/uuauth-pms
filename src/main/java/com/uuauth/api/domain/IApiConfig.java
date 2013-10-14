/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    IApiConfig.java
 * Creator:     qiaofeng
 * Create-Date: 上午09:33:25
 */
package com.uuauth.api.domain;

/**
 * TODO
 * 
 * @author qiaofeng
 * @version $Id: IApiConfig, v 0.1 2012-10-10 上午09:33:25 Exp $
 */
public interface IApiConfig {
	
	String getToken();
	
	String getPassword();
	
	String getApiUrl();
	
	String getSuffix();
	
}
