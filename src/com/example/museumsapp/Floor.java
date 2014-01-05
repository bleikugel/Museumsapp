package com.example.museumsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Floor implements Serializable{

	/**
	 * Eine Klasse fuer Etagen.
	 */
	private static final long serialVersionUID = -5769582552158037038L;
	
	private int uid;
	private String name;
	private int bmID;
	private int[][] boundarybox;
	private List<Picture> pictures;
	public Floor()
	{
		pictures = new ArrayList<Picture>();
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

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

}
