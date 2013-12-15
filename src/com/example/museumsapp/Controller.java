package com.example.museumsapp;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
		this.scX = 1;
		this.scY = 1;
		showBuilding(R.drawable.campusplan,campusCode);
	}
	

	
	public Activity getCurrentActivity()
	{
		return currentActivity;
	}
	
	public void setCurrentActivity(Activity a)
	{
		this.currentActivity = a;
	}
	
	public void showBuilding(int id,int currentCode)
	{
		currCode = currentCode;
        Intent i = new Intent(currentActivity.getApplicationContext(), GenericActivity.class);
        i.putExtra("Fullscreen", true);
        i.putExtra("Overview", id);
        currentActivity.startActivityForResult(i,currCode);	
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
	
	
	public boolean showCurrentBuilding(int state, Intent data)
	{
	    if (data.hasExtra("coords")) {
	    	int[] coords = data.getExtras().getIntArray("coords");

	    	setCurrentCoordinates(coords[0], coords[1]);
	    	
	    	
	    	int curr = (getCurrentBuildingByCoordinates(coords[0], coords[1], scX / 800, scY / 600));
	    	
	    	if(curr == 0)
	    		showBuilding(R.drawable.hauptgebaeude,hqCode);
	    	else if(curr == 8)
	    	{
	    		this.currCode = hoCode;
	    		showBuildingWithPictures();
	    	}
	    	else showBuilding(R.drawable.campusplan,campusCode);
	    	return true;
	    }
	    
	    return false;
	}
	
	public void showBuildingWithPictures()
	{
		for(int j = 0; j < 1; j++)
		{

			if(currCode == hoCode)
			{
				Intent i = new Intent(currentActivity.getApplicationContext(), GenericActivity.class);
				i.putExtra("Fullscreen", true);
				i.putExtra("Overview", R.drawable.hoersaalgebaeude);
				i.putExtra("PictureCounter", 1);
				i.putExtra("Picture1", R.drawable.picasso_01);
				currentActivity.startActivityForResult(i,currCode);	
			}
		}
	}
	
	public void setScales(double x, double y)
	{
		this.scX = x;
		this.scY = y;
	}

}
