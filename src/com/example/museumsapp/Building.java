package com.example.museumsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Building implements Serializable{


	private static final long serialVersionUID = -8525177572661770489L;
	private int uid;
	private String name;
	private int bmID;
	private int[][] boundarybox;
	private List<Picture> pictures;
	public Building()
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
