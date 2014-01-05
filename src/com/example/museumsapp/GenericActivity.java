package com.example.museumsapp;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;

// Eine einfache generische Activity.
// Flexibles darstellen von Bildern + Abfrage der TouchPosition.
public class GenericActivity extends Activity {

	private int x;
	private int y;
	private double scX;
	private double scY;
	private double scalX;
	private double scalY;
	private FrameLayout fl = null;
	private int result;
	private List<Building> buildings;
	private Building currentBuilding;
	private Floor currentFloor;
	private Picture currentPicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.x = 0;
		this.y = 0;  
		this.buildings = new ArrayList<Building>();
		this.scX = (double)getResources().getDisplayMetrics().widthPixels;
		this.scY = (double)getResources().getDisplayMetrics().heightPixels;
		this.currentBuilding = null;
		this.currentFloor = null;
		this.currentPicture = null;
		
		Intent intent = getIntent();
		if(intent.getBooleanExtra("Fullscreen", true)) setFullscreen();
		setContentView(R.layout.activity_generic);
		
		// Panorama-Modus
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		fl = (FrameLayout) findViewById(R.id.frl1);
		// Hintergrund setzen
		
		if(intent.getIntExtra("Overview", -1) != -1)
			setBuildingPicture(intent.getIntExtra("Overview", -1));
		else if(intent.getSerializableExtra("Building") != null)
		{
			Object obj =  (Object) intent.getSerializableExtra("Building");
			if(obj instanceof Building)
			{
				currentBuilding = (Building) obj;
				setBuildingPicture(((Building)obj).getBitmapID());
			}
		}
		else if(intent.getSerializableExtra("Floor") != null)
		{
			Object obj =  (Object) intent.getSerializableExtra("Floor");
			if(obj instanceof Floor)
			{
				currentFloor = (Floor) obj;
				setBuildingPicture(((Floor)obj).getBitmapID());
			}
		}
		
	    if(intent.getBooleanExtra("BuildingsBool", false))
		{
			Object[] objArray =  (Object[]) intent.getSerializableExtra("Buildings");
			initClickableObjects(objArray);
		}
		
		
		// Die einzelnen Bilder setzen, falls vorhanden
		if(currentFloor != null || (currentBuilding != null && currentBuilding.getCurrentFloor() != null))
		{
			if(currentFloor != null)
				for(Picture p : currentFloor.getPictures())
					setPicture(p);
			else
				for(Picture p : currentBuilding.getCurrentFloor().getPictures())
					setPicture(p);
			
		}
	}
	@Override
    protected void onResume() {
        super.onResume();
    }
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		this.result = RESULT_CANCELED;		
	}
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {

	    this.x = (int)event.getX();
	    this.y = (int)event.getY();
	    int[] vec = new int[] {this.x,this.y};
	    // Liste nach geklicktem Objekt durchsuchen
	    if(buildings.size() > 0)
	    {
	    	
	    	for(Building b : buildings)
	    	{
	    		if(isVectorInBoundaryBox(vec, b.getBoundarybox()[0][0], b.getBoundarybox()[0][1], b.getBoundarybox()[1][0], b.getBoundarybox()[1][1]))
	    		{
	    		    this.result = RESULT_OK;
	    		    this.currentBuilding = b;
	    		    this.currentPicture = null;
	    		    this.finish();
	    		}
	    		
	    	}
	    }
	    
	    if(this.currentBuilding != null && this.currentBuilding.getCurrentFloor() == null)
	    {
	    	for(Floor f : currentBuilding.getFloors())
	    	{
	    		if(isVectorInBoundaryBox(vec, f.getBoundarybox()[0][0], f.getBoundarybox()[0][1], f.getBoundarybox()[1][0], f.getBoundarybox()[1][1]))
	    		{
	    		    this.result = RESULT_OK;
	    		    this.currentFloor = f;
	    		    this.finish();
	    		}
	    		
	    	}    	
	    }	    
	    else if(this.currentFloor != null  ||  (this.currentBuilding != null && this.currentBuilding.getCurrentFloor() != null))
	    	if(this.currentFloor != null)
	    		for(Picture p : this.currentFloor.getPictures())
    			{
	    			if(p.getBoundarybox() != null && isVectorInBoundaryBox(vec, p.getBoundarybox()[0][0], p.getBoundarybox()[0][1], p.getBoundarybox()[1][0], p.getBoundarybox()[1][1]))
	    			{
	    		    	this.result = RESULT_OK;
	    		    	this.currentBuilding = null;
	    		    	this.currentFloor = null;
	    		    	this.currentPicture = p;
	    		    	this.finish();
	    			}
    			}
	    	else
	    		for(Picture p : this.currentBuilding.getCurrentFloor().getPictures())
	    		{
		    		if(p.getBoundarybox() != null && isVectorInBoundaryBox(vec, p.getBoundarybox()[0][0], p.getBoundarybox()[0][1], p.getBoundarybox()[1][0], p.getBoundarybox()[1][1]))
		    		{
		    		    this.result = RESULT_OK;
		    		    this.currentBuilding = null;
		    		    this.currentFloor = null;
		    		    this.currentPicture = p;
		    		    this.finish();
		    		}
	    		}
	    	
	return true;
	}
	
	public void setBuildingPicture(int pictureID)
	{
		if(pictureID == -1) return;
		FrameLayout.LayoutParams params;
		params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.leftMargin = 0;
		params.topMargin = 0;

		ImageView image = new ImageView(this.getApplicationContext());
		image.setImageDrawable(getResources().getDrawable(pictureID));
		image.setScaleType(ScaleType.FIT_XY);

		Options opts = new Options();
		opts.inScaled = false;
		Bitmap bm = BitmapFactory.decodeResource(getResources(), pictureID, opts);
		
		// Die Bilder sind meist kleiner oder groesser als das Display,
		// daher Skalierung setzen.
		this.scalX = this.scX / bm.getWidth() ;
		this.scalY = this.scY / bm.getHeight() ;
		fl.addView(image, params);
	}
	
	public void setPicture(Picture p)
	{
		if(p.getPictureID() == -1) return;
		FrameLayout.LayoutParams params;
		params = new FrameLayout.LayoutParams(64, 64);
		params.leftMargin = (int)(p.getxCoord() * scalX);
		params.topMargin = (int)(p.getyCoord() * scalY);
		
		ImageView image = new ImageView(this.getApplicationContext());
		try
		{
			image.setImageBitmap(decodeSampledBitmapFromResource(getResources(), p.getPictureID(), 64, 64));
			p.setBoundarybox(new int[][]{{p.getxCoord()+64,p.getyCoord()+64},{p.getxCoord(),p.getyCoord()}});
		}
		catch (OutOfMemoryError e)
		{
			System.gc();
			params = new FrameLayout.LayoutParams(32, 32);
			params.leftMargin = (int)(p.getxCoord() * scalX);
			params.topMargin = (int)(p.getyCoord() * scalY);
			image.setImageBitmap(decodeSampledBitmapFromResource(getResources(), p.getPictureID(), 32, 32));	
			p.setBoundarybox(new int[][]{{p.getxCoord()+64,p.getyCoord()+64},{p.getxCoord(),p.getyCoord()}});
		}
		image.setScaleType(ScaleType.FIT_XY);

		fl.addView(image, params);
	}
	
	private void setFullscreen()
	{
		// Ohne Titel + Vollbild
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);	
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
	
	@Override
	public void finish() {
	  Intent data = new Intent();

	  if(this.currentBuilding != null && this.currentFloor == null)
		  data.putExtra("Building",this.currentBuilding);
	  else if(this.currentBuilding != null && this.currentFloor != null)
		  data.putExtra("Floor",this.currentFloor);
	  else if(this.currentPicture != null)
		  data.putExtra("Picture",this.currentPicture);
	  
	  setResult(result, data);
	  super.finish();
	} 
	
	public boolean isVectorInBoundaryBox(int[] vec,int x1, int y1, int x2, int y2)
	{
		
		if((vec[0] < x1 * scalX && vec[1] < y1 * scalY) && (vec[0] > x2 * scalX && vec[1] > y2 * scalY ))
			return true;
		
		return false;
	}
	
	
	public void initClickableObjects(Object[] objs)
	{
		Object obj= null;
		if(objs!=null)
		{
			for(int i=0;i<objs.length;i++)
			{
				obj = objs[i];
				if(obj instanceof Building )
				{
					Building b = (Building) obj;
					buildings.add(b);
				}
			}

		}
		

		   
		
	}

}
