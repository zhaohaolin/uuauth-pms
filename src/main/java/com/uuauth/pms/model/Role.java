package com.uuauth.pms.model;

import java.util.List;

public class Role {
	
	private int				id;
	private String			name;
	private String			description;
	private String			powers;
	private String			powers1;
	private List<String>	powerIds;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPowers() {
		return powers;
	}
	
	public void setPowers(String powers) {
		this.powers = powers;
	}
	
	public String getPowers1() {
		return powers1;
	}
	
	public void setPowers1(String powers1) {
		this.powers1 = powers1;
	}
	
	public List<String> getPowerIds() {
		return powerIds;
	}
	
	public void setPowerIds(List<String> powerIds) {
		this.powerIds = powerIds;
	}
}
