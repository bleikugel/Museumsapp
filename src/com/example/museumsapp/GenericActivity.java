package com.example.museumsapp;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.app.Activity;
import android.content.Intent;

public class GenericActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		  
		Intent i = getIntent();
		
		if(i.getBooleanExtra("Fullscreen", true)) setFullscreen();
		setContentView(R.layout.activity_generic);
		setPicture(i.getIntExtra("Overview", R.drawable.logo));

		
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

}
