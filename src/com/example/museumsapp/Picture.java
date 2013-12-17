package com.example.museumsapp;

import java.io.Serializable;

public class Picture implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int uid;
	private String name;
	private String creator;
	private String description;
	private int xCoord;
	private int yCoord;
	private int pictureID;
	private int[][] boundarybox;
	
	public Picture()
	{
		name = "";
		creator = "";
		description = "";
		
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getUid() {
		return uid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setPictureID(int pictureID) {
		this.pictureID = pictureID;
	}

	public int getPictureID() {
		return pictureID;
	}

	public void setBoundarybox(int[][] boundarybox) {
		this.boundarybox = boundarybox;
	}

	public int[][] getBoundarybox() {
		return boundarybox;
	}

	

}
