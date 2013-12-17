package com.example.museumsapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Controller extends Application{
	
	private int campusCode = 0x808;
	private int hqCode = 0x809;
	private int hoCode = 0x80A;
	private double scX;
	private double scY;
	private int currCode = 0x808;
	private int x,y;
	private Activity currentActivity;
	public Controller (Activity a )
	{
		this.currentActivity = a;
		init();
	}
	@Override
    public void onCreate() {
        super.onCreate();
  }
	
	public void init()
	{
		showCampus(R.drawable.campusplan,campusCode);
	}
	

	
	public Activity getCurrentActivity()
	{
		return currentActivity;
	}
	
	public void setCurrentActivity(Activity a)
	{
		this.currentActivity = a;
	}
	
	public void showCampus(int id,int currentCode)
	{
		currCode = currentCode;

        Intent i = new Intent(currentActivity.getApplicationContext(), GenericActivity.class);
        i.putExtra("Fullscreen", true);
        i.putExtra("Overview", id);
        i.putExtra("BuildingsBool",true);
        i.putExtra("Buildings", buildBuildings());
        currentActivity.startActivityForResult(i,currCode);	
	}
	
	public void showBuilding(Building building)
	{
		currCode = building.getUid();
        Intent i = new Intent(currentActivity.getApplicationContext(), GenericActivity.class);
        i.putExtra("Fullscreen", true);
        i.putExtra("Building", building);
        currentActivity.startActivityForResult(i,currCode);	
	}
	
	public Building[] buildBuildings()
	{
		// Liste wuerde sich eigentlich anbieten...
		Building[] builds = new Building[2];
		Options opts = new Options();
		opts.inScaled = false;
		
		// Hauptgebaeude
		Building b = new Building();
		
		b.setUid(hqCode);
		b.setName("Hauptgebauede");
		b.setBoundarybox(new int[][]{{389,263},{276,121}});
		b.setBitmapID(R.drawable.hauptgebaeude);
		builds[0] = b;
		
		// Hoersaalgebaeude
		b = new Building();
		b.setUid(hoCode);
		b.setName("Hoersaalgebaeude");
		b.setBoundarybox(new int[][]{{576,177},{438,60}});
		b.setBitmapID(R.drawable.hoersaalgebaeude);
		
		// Bilder setzen
		
		List<Picture> pictures = new ArrayList<Picture>();
		
		//int[] Pictures = new int[] {R.drawable.b1,R.drawable.b2,R.drawable.b3};
		//int[][] coords = new int[][] {{(int)(196 * scX / 1350),(int)(271 * scY / 538)},{(int)(380* scX / 1350),(int)(390 * scY / 538)},{(int)(1231 * scX / 1350),(int)(375 * scY / 538)}};
		//i.putExtra("PictureCounter", Pictures.length);
		//i.putExtra("Pictures", Pictures);
		//i.putExtra("Coordinates", coords);
		Picture p = new Picture();
		p.setName("Bild 1");
		p.setCreator("..............");
		p.setDescription("Hübsch, oder ?!");
		p.setUid(231);
		p.setPictureID(R.drawable.b1);
		p.setxCoord(196);
		p.setyCoord(271);
		pictures.add(p);
		
		p = new Picture();
		p.setName("Bild 2");
		p.setCreator("...........");
		p.setDescription("Hübsch, oder ?!");
		p.setUid(34);
		p.setPictureID(R.drawable.b2);
		p.setxCoord(380);
		p.setyCoord(390);
		pictures.add(p);
		
		p = new Picture();
		p.setName("Bild 3");
		p.setCreator("..............");
		p.setDescription("Hübsch, oder ?!");
		p.setUid(3344);
		p.setPictureID(R.drawable.b3);
		p.setxCoord(1231);
		p.setyCoord(375);
		pictures.add(p);
		
		b.setPictures(pictures);
		
		builds[1] = b;
		
		return builds;
		
	}
	
	// Koordinaten setzen.
	public void setCurrentCoordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	// Koordinaten holen.
	public int[] getCurrentCoordinates()
	{
		return new int[] {this.x,this.y};
	}
	
	// Gibt das jeweilige Gebaeude, codiert als Nummer, zurueck.
	// Erfassung erfolgt anhand einer Bounding-Box. 
	// Alle Gebauede sind in einer Rechteckstruktur erfasst.
	// Die Koordinaten muessen entsprechend dem Original skaliert werden. (800 x 600)
	// Rueckgabewerte:
	//				   -1 - Kein bekanntes Gebaeude
	//					0 - Hauptgebaeude
	//					1 - Wohnhaus 1
	//					2 - Wohnhaus 2
	//					3 - Wohnhaus 3
	//					4 - Wohnhaus 4
	//					5 - Wohnhaus 5
	//					6 - Wohnhaus 6
	//					7 - Werkstatt
	//					8 - Hoersaalgebaeude
	public int getCurrentBuildingByCoordinates(int x, int y,double scalx,double scaly)
	{
		
		if((x < 389 * scalx && y < 263 * scaly) && (x > 276 * scalx && y > 121 * scaly ))
			return 0;
		else if((x < 576 * scalx && y < 177 * scaly) && (x > 438 * scalx && y > 60 * scaly ))
			return 8;
		
		return -1;
	}
	
	public int getCurrentPictureByCoordinates(int x, int y,double scalx,double scaly)
	{
		
		if((x < 389 * scalx && y < 263 * scaly) && (x > 276 * scalx && y > 121 * scaly ))
			return 0;
		else if((x < 576 * scalx && y < 177 * scaly) && (x > 438 * scalx && y > 60 * scaly ))
			return 8;
		
		return -1;
	}
	
	
	public boolean showCurrentBuildingOrPicture(int state, Intent data)
	{
	    if (data.hasExtra("Building")) 
	    {
	    	Object building = (Object)data.getSerializableExtra("Building");

	    	if(building instanceof Building)
	    	{
	    	//setCurrentCoordinates(coords[0], coords[1]);
	    		showBuilding((Building)building);
	    	/*
	    		if(state == campusCode)
	    		{
	    	
	    			if(curr == 0)
	    				showBuilding(R.drawable.hauptgebaeude,hqCode);
	    			else if(curr == 8)
	    			{
	    				this.currCode = hoCode;
	    				showBuildingWithPictures();
	    			}
	    			else showBuilding(R.drawable.campusplan,campusCode);
	    		}
	    		else if(state == hoCode)
	    		{
	    			int curr = (getCurrentPictureByCoordinates(coords[0], coords[1], scX / 800, scY / 600));
	    			curr = -1;
	    			showBuildingWithPictures();
	    			
	    		}
	    	return true;
	    	*/
	    	return true;
	    	}
	    	else return false;
	    }
	    else if(data.hasExtra("Picture"))
	    {
	    	Object picture = (Object)data.getSerializableExtra("Picture");
	    	if(picture instanceof Picture)
	    	{
	    		showSinglePictureInfo((Picture)picture);
	    	}
	    }
	    
	    return false;
	}
	
	public void showSinglePictureInfo(Picture pic)
	{
        Intent i = new Intent(currentActivity.getApplicationContext(), PictureInfoActivity.class);
        //i.putExtra("Fullscreen", true);
        i.putExtra("Picture", pic);
        currentActivity.startActivityForResult(i,currCode);	
	}
	
	public void setScales(double x, double y)
	{
		this.scX = x;
		this.scY = y;
	}

}
