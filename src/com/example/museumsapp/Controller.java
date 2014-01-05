package com.example.museumsapp;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.BitmapFactory.Options;

public class Controller extends Application{
	
	private int campusCode = 0x808;
	private int hqCode = 0x809;
	private int hoCode = 0x80A;
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
	
	public void showFloor(Floor floor)
	{
		currCode = floor.getUid();
        Intent i = new Intent(currentActivity.getApplicationContext(), GenericActivity.class);
        i.putExtra("Fullscreen", true);
        i.putExtra("Floor", floor);
        currentActivity.startActivityForResult(i,currCode);	
	}
	
	// Hier bietet sich dann der Einsatz der Datenbank an, wenn denn mal eine da wäre...
	public Building[] buildBuildings()
	{
		// Liste wuerde sich eigentlich anbieten...
		Building[] builds = new Building[3];
		List<Floor> floors =  new ArrayList<Floor>();
		List<Picture> pictures = new ArrayList<Picture>();
		
		Options opts = new Options();
		opts.inScaled = false;
		
		// Hauptgebaeude
		Building b = new Building();
		Floor f = new Floor();
		
		b.setUid(hqCode);
		b.setName("Hauptgebauede");
		b.setBoundarybox(new int[][]{{389,263},{276,121}});
		b.setBitmapID(R.drawable.hauptgebaeude);
		
		// Etage setzen
		f.setUid(0x0);
		f.setName("Etage 1");
		f.setBoundarybox(new int[][]{{100000000,100000000},{0,0}});
		f.setBitmapID(R.drawable.hqfloor);
		
		// Bild setzen
		Picture p = new Picture();
		p.setName("Bild 1");
		p.setCreator("Wer malt soetwas?!");
		p.setDescription("Ich muss unbedingt mal herausfinden, zu welchen der Bilder die Einträge in der Excel-Tabelle gehören.");
		p.setUid(231);
		p.setPictureID(R.drawable.b5);
		p.setxCoord(196);
		p.setyCoord(271);
		pictures.add(p);
		f.setPictures(pictures);
		
		floors.add(f);
		
		b.setFloors(floors);
		builds[0] = b;
		
		// Hoersaalgebaeude
		b = new Building();
		b.setUid(hoCode);
		b.setName("Hoersaalgebaeude");
		b.setBoundarybox(new int[][]{{576,177},{438,60}});
		b.setBitmapID(R.drawable.hoersaalgebaeude);
		
        f = new Floor();
		f.setUid(0x0);
		f.setName("Etage 1");
		f.setBoundarybox(new int[][]{{576,177},{438,60}});
		f.setBitmapID(R.drawable.hoersaalgebaeude);
		
		// Bilder setzen
		pictures = new ArrayList<Picture>();
		p = new Picture();
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
		
		f.setPictures(pictures);
		// Bei Gebaeuden mit nur einer Etage muss diese explizit gesetzt werden!
		b.setCurrentFloor(f);
		builds[1] = b;
		
		// Seminargebaeude
		b = new Building();
	 	f = new Floor();
		
		b.setUid(hqCode);
		b.setName("Seminargebaeude");
		b.setBoundarybox(new int[][]{{531,248},{462,204}});
		b.setBitmapID(R.drawable.seminarbuilding);
		
		f.setUid(0x0);
		f.setName("Etage 1");
		f.setBoundarybox(new int[][]{{576,177},{438,60}});
		f.setBitmapID(R.drawable.seminarbuilding);
		b.setCurrentFloor(f);
		
		builds[2] = b;
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
	
	public boolean showCurrentBuildingOrPicture(int state, Intent data)
	{
	    if (data.hasExtra("Building")) 
	    {
	    	Object building = (Object)data.getSerializableExtra("Building");

	    	if(building instanceof Building)
	    	{
	    		showBuilding((Building)building);
	    		return true;
	    	}
	    	else return false;
	    }
	    else if(data.hasExtra("Floor"))
	    {
	    	Object floor = (Object)data.getSerializableExtra("Floor");
	    	if(floor instanceof Floor)
	    	{
	    		showFloor((Floor)floor);
	    	}
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
	

}
