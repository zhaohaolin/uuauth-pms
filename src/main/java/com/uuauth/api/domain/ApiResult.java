/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    HttpResult.java
 * Creator:     joe.zhao
 * Create-Date: 上午10:37:47
 */
package com.uuauth.api.domain;

import java.io.Serializable;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: HttpResult, v 0.1 2012-5-25 上午10:37:47 Exp $
 */
public class ApiResult implements Serializable {
	
	/**  */
	private static final long	serialVersionUID	= -2130972796564160925L;
	/**
	 * return api request status:[0:succes,1:fauiler]
	 */
	private int					status;
	/**
	 * return http api message
	 */
	private String				message;
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
