package com.example.museumsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Building implements Serializable{
	/**
	 * Eine einfache Klasse fuer Gebaeude, welches aus den dazugehoerigen Etagen besteht.
	 */

	private static final long serialVersionUID = -8525177572661770489L;
	private int uid;
	private String name;
	private int bmID;
	private int[][] boundarybox;
	private List<Floor> floors;
	private Floor currentFloor;
	public Building()
	{
		floors = new ArrayList<Floor>();
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getUid() {
		return uid;
	}

	public void setBoundarybox(int[][] boundarybox) {
		this.boundarybox = boundarybox;
	}

	public int[][] getBoundarybox() {
		return boundarybox;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBitmapID(int bmID) {
		this.bmID = bmID;
	}

	public int getBitmapID() {
		return bmID;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public List<Floor> getFloors() {
		return this.floors;
	}

	public void setCurrentFloor(Floor currentFloor) {
		this.currentFloor = currentFloor;
	}

	public Floor getCurrentFloor() {
		return currentFloor;
	}
	
	
}
