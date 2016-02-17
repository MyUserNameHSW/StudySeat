package com.cheezhi.bean;

import java.io.Serializable;

public class NotificationMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String theme;
	private String content;
	private String department;
	private String time;
	private int show;
	private int state;
	private boolean isChecked ;

	public NotificationMsg(int id, String theme, String content,
			String department, String time,int state) {
		super();
		this.id = id;
		this.theme = theme;
		this.content = content;
		this.department = department;
		this.time = time;
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "NotificationMsg [id=" + id + ", theme=" + theme + ", content="
				+ content + ", department=" + department + ", time=" + time
				+ ", state=" + state + "]";
	}
}
