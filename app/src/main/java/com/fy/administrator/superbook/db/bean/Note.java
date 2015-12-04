package com.fy.administrator.superbook.db.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "note")
public class Note extends Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String info;
	@DatabaseField
	private long date;
	@DatabaseField
	private String address;
	@DatabaseField
	private String image;
	@DatabaseField
	private String record;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage() {
		return image;
	}
	public String getRecord() {
		return record;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setRecord(String record) {
		this.record = record;
	}
}
