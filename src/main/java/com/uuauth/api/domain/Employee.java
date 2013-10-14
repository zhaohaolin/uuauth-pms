package com.uuauth.api.domain;

/**
 */
public class Employee {
	
	private Integer	id;
	private String	loginName;
	private String	loginPassword;
	private String	name;
	private Integer	sex;
	private Integer	depid;
	private Integer	postid;
	private String	tel;
	private String	extTel;
	private String	email;
	private String	note;
	private String	resume;
	private String	clientSn;
	private Integer	status;
	private long	lastLoginTime;
	private String	lastLoginIp;
	private Integer	createTime;
	private String	uucallNumber;
	private String	nickName;
	private String	sessionToken;
	private String	roles;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLoginName() {
		return this.loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getLoginPassword() {
		return this.loginPassword;
	}
	
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSex() {
		return this.sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	public Integer getDepid() {
		return this.depid;
	}
	
	public void setDepid(Integer depid) {
		this.depid = depid;
	}
	
	public Integer getPostid() {
		return this.postid;
	}
	
	public void setPostid(Integer postid) {
		this.postid = postid;
	}
	
	public String getTel() {
		return this.tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getExtTel() {
		return this.extTel;
	}
	
	public void setExtTel(String extTel) {
		this.extTel = extTel;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getResume() {
		return this.resume;
	}
	
	public void setResume(String resume) {
		this.resume = resume;
	}
	
	public String getClientSn() {
		return this.clientSn;
	}
	
	public void setClientSn(String clientSn) {
		this.clientSn = clientSn;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public long getLastLoginTime() {
		return this.lastLoginTime;
	}
	
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}
	
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	public Integer getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	
	public String getUucallNumber() {
		return this.uucallNumber;
	}
	
	public void setUucallNumber(String uucallNumber) {
		this.uucallNumber = uucallNumber;
	}
	
	public String getNickName() {
		return this.nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getSessionToken() {
		return this.sessionToken;
	}
	
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	public String getRoles() {
		return this.roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
}
