package com.fy.administrator.superbook.db.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "manyBook")
public class ManyBook extends Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String info;
	@DatabaseField
	private int money;
	@DatabaseField
	private long date;
	@DatabaseField
	private boolean isSolve;
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
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public boolean isSolve() {
		return isSolve;
	}
	public void setSolve(boolean isSolve) {
		this.isSolve = isSolve;
	}
	@Override
	public String toString() {
		return "ManyBook [id=" + id + ", info=" + info + ", money=" + money
				+ ", date=" + date + ", isSolve=" + isSolve + "]";
	}
}
