/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    PmsConst.java
 * Creator:     joe.zhao
 * Create-Date: 上午11:54:35
 */
package com.uuauth.pms;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: PmsConst, v 0.1 2012-5-20 上午11:54:35 Exp $
 */
public interface PmsConst {
	
	/**
	 * 保存到session中的用户信息key
	 */
	final static String	sessionUser		= "sessionUser";
	
	/**
	 * 保存到session中的用户角色信息key
	 */
	final static String	sessionRole		= "sessionRole";
	
	/**
	 * 保存到session中的用户权限信息key
	 */
	final static String	sessionPowers	= "sessionPowers";
	
	/**
	 * 客户端cookie的key
	 */
	final static String	TOKEN_COOKIE	= "__token__";
	
	/**
	 * 计算在线人数量的application 的key
	 */
	final static String	ONLINE_NUM		= "_online_num_";
	
}
