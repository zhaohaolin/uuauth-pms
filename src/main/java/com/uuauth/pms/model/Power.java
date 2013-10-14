/**
 * 
 */
package com.uuauth.pms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_pms_powers")
public class Power {
	
	@Id
	private String		id;
	private String		name		= "";
	private String		power		= "";
	private String		url			= "";
	private String		description	= "";
	private boolean		menu		= false;
	private String		oldId;
	private int			oldLength;
	private boolean		select;
	private List<Power>	list		= new ArrayList<Power>();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOldId() {
		return oldId;
	}
	
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPower() {
		return power;
	}
	
	public void setPower(String power) {
		this.power = power;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isMenu() {
		return menu;
	}
	
	public void setMenu(boolean menu) {
		this.menu = menu;
	}
	
	public List<Power> getList() {
		return list;
	}
	
	public void setList(List<Power> list) {
		this.list = list;
	}
	
	public int getOldLength() {
		return oldLength;
	}
	
	public void setOldLength(int oldLength) {
		this.oldLength = oldLength;
	}
	
	public boolean isSelect() {
		return select;
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}
}
