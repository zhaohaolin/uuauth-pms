/*
 * CopyRight (c) 2005-2012 Taotaosou Co, Ltd. All rights reserved.
 * Filename:    SqliteDB.java
 * Creator:     joe.zhao
 * Create-Date: 下午9:07:11
 */
package com.uuauth.pms.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: SqliteDB, v 0.1 2013年10月15日 下午9:07:11 Exp $
 */
public class SqliteUtils {
	
	private Connection					conn		= null;
	private String						username;
	private String						password;
	private String						url;
	private String						driver;
	private static final String			CONFIG_FILE	= "pms.properties";
	private static final Logger			LOG			= LoggerFactory
															.getLogger(SqliteUtils.class);
	
	private static final SqliteUtils	instance	= new SqliteUtils();
	
	public final static SqliteUtils getInstance() {
		return instance;
	}
	
	private SqliteUtils() {
		try {
			init();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws ClassNotFoundException {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(CONFIG_FILE);
		if (null != is) {
			Properties cfg = new Properties();
			try {
				cfg.load(is);
				if (null != cfg) {
					// pms.db.username
					String temp = cfg.getProperty("pms.db.username");
					if (StringUtils.isNotEmpty(temp)) {
						this.username = temp;
					}
					
					// pms.db.password
					temp = cfg.getProperty("pms.db.password");
					if (StringUtils.isNotEmpty(temp)) {
						this.password = temp;
					}
					
					// pms.db.url
					temp = cfg.getProperty("pms.db.url");
					if (StringUtils.isNotEmpty(temp)) {
						this.url = temp;
					}
					
					// pms.db.driver
					temp = cfg.getProperty("pms.db.driver");
					if (StringUtils.isNotEmpty(temp)) {
						this.driver = temp;
					}
					
					openConnection();
				}
			} catch (IOException e) {
				throw new RuntimeException("Invalid configuration file "
						+ CONFIG_FILE, e);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					LOG.error("Unexpected Error!", e);
				}
			}
		}
	}
	
	public void openConnection() {
		// open db conn
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public void closeConnection() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
