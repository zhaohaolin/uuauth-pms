package com.uuauth.api.domain;

/**
 */
public class Department {
	// Fields
	
	private Integer	id;
	private Integer	parentId	= 0;
	private String	name;
	
	// Constructors
	/** default constructor */
	public Department() {
		//
	}
	
	/** minimal constructor */
	public Department(Integer id) {
		this.id = id;
	}
	
	/** full constructor */
	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// Property accessors
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
