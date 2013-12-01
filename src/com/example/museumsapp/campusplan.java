package com.example.museumsapp;

import android.app.Activity;
import android.content.Intent;


// Eine eigene Klasse fuer die Campus-Uebersicht.
// Im Grunde nur dazu da, um zu erfahren, in welchem Gebauede man sich derzeit aufhaelt.
public class campusplan {
	
	private int currCode = 0x808;
	private int x,y;
	private Activity currentActivity;
	public campusplan(Activity act)
	{
		currentActivity = act;
	}
	
	
	public void showPicture(int id,int currentCode)
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
	public int getCurrentBuildingByCoordinates(int x, int y,double scalx,double scaly)
	{
		
		if((x < 389 * scalx && y < 263 * scaly) && (x > 276 * scalx && y > 121 * scaly ))
			return 0;
		
		return -1;
	}
	
	
	
}
