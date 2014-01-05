package com.example.museumsapp;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MainActivity extends Activity {



	ViewPager mViewPager;
	private Controller ctrl; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		ctrl = new Controller(this);
	}



	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK) {
		  ctrl.showCurrentBuildingOrPicture(requestCode, data);
	  }
	  else if(resultCode == RESULT_CANCELED &&  requestCode != 0x808)
	  {
		  ctrl.init();
	  }
	  else finish();

	}

	

}
