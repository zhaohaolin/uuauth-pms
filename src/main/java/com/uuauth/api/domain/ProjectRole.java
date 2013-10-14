package com.uuauth.api.domain;

/**
 * ProjectRoleId generated by MyEclipse - Hibernate Tools
 */
public class ProjectRole implements java.io.Serializable {
	// Fields
	
	/** serialVersionUID */
	private static final long	serialVersionUID	= -4475736330660134026L;
	/**
	 * fields
	 */
	private int					id;
	private String				roleName;
	private String				roleDesc;
	private String				proToken;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	// Property accessors
	public String getRoleName() {
		return this.roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleDesc() {
		return this.roleDesc;
	}
	
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public String getProToken() {
		return this.proToken;
	}
	
	public void setProToken(String proToken) {
		this.proToken = proToken;
	}
}
