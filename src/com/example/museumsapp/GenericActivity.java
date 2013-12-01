package com.example.museumsapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

// Eine einfache generische Activity.
// Flexibles darstellen von Bildern + Abfrage der TouchPosition.
public class GenericActivity extends Activity {

	private int x;
	private int y;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.x = 0;
		this.y = 0;  
		
		Intent i = getIntent();

		if(i.getBooleanExtra("Fullscreen", true)) setFullscreen();
		setContentView(R.layout.activity_generic);
		
		// Panorama-Modus
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// Image setzen
		setPicture(i.getIntExtra("Overview", R.drawable.logo));

		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

	    this.x = (int)event.getX();
	    this.y = (int)event.getY();
	    this.finish();
	return true;
	}
	
	public void setPicture(int pictureID)
	{
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        iv.setScaleType(ScaleType.FIT_XY);
        iv.setImageResource(pictureID);	
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
	  setResult(RESULT_OK, data);
	  super.finish();
	} 

}
