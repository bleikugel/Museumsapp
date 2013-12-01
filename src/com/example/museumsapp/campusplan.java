package com.example.museumsapp;

import android.app.Activity;
import android.content.Intent;

public class campusplan {
	
	public campusplan(Activity act)
	{
        Intent i = new Intent(act.getApplicationContext(), GenericActivity.class);
        i.putExtra("Fullscreen", true);
        i.putExtra("Overview", R.drawable.campusplan);
        act.startActivity(i);
        
	}
}
