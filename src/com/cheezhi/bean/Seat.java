package com.cheezhi.bean;

import java.io.Serializable;

public class Seat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int s_id;
	private int area;
	private String room_name;
	private int seatNum;
	private int type;

	public Seat(int s_id, int area, String room_name, int seatNum, int type) {
		super();
		this.s_id = s_id;
		this.area = area;
		this.room_name = room_name;
		this.seatNum = seatNum;
		this.type = type;
	}

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public int getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Seat [s_id=" + s_id + ", area=" + area + ", room_name=" + room_name + ", seatNum=" + seatNum + ", type="
				+ type + "]";
	}

}
