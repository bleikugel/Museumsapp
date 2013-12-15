package com.example.museumsapp;

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

// Eine einfache generische Activity.
// Flexibles darstellen von Bildern + Abfrage der TouchPosition.
public class GenericActivity extends Activity {

	private int x;
	private int y;
	private FrameLayout fl = null;
	private int result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.x = 0;
		this.y = 0;  
		
		Intent intent = getIntent();
		if(intent.getBooleanExtra("Fullscreen", true)) setFullscreen();
		setContentView(R.layout.activity_generic);
		
		// Panorama-Modus
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		fl = (FrameLayout) findViewById(R.id.frl1);
		// Hintergrund setzen
		setBuildingPicture(intent.getIntExtra("Overview", -1));
		
		// Die einzelnen Bilder setzen, falls vorhanden
		if(intent.getIntExtra("PictureCounter", -1) > 0)
			for(int i = 0; i< intent.getIntExtra("PictureCounter", -1); i++)
				setPicture(intent.getIntExtra("Picture1", -1));
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
	    this.result = RESULT_OK;
	    this.finish();
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

		 
		fl.addView(image, params);
	}
	
	public void setPicture(int pictureID)
	{
        //ImageView iv = (ImageView) findViewById(R.id.imageView1);
		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//params.setMargins(100,75, 0,0);
		//iv.setLayoutParams(params);
        //iv.setImageResource(pictureID);	
		if(pictureID == -1) return;
		FrameLayout.LayoutParams params;
		params = new FrameLayout.LayoutParams(100, 100);
		params.leftMargin = 0;
		params.topMargin = 0;
		
		ImageView image = new ImageView(this.getApplicationContext());
		image.setImageDrawable(getResources().getDrawable(pictureID));
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
	
	@Override
	public void finish() {
	  Intent data = new Intent();
	  int[] coords = new int[2];
	  coords[0] = this.x;
	  coords[1] = this.y;
	  data.putExtra("coords",coords);
	  setResult(result, data);
	  super.finish();
	} 

}
