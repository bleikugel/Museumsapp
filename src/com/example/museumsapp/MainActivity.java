package com.example.museumsapp;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {


	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	private int campusCode = 0x808;
	private int hqCode = 0x809;
	private campusplan cp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
	
		cp = new campusplan(this);
		cp.showPicture(R.drawable.campusplan,campusCode);

		/*
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.hide();
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);


		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) 
		{
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}


    
	public static class DummySectionFragment extends Fragment {

		private int imgid[]={R.drawable.picasso_01,R.drawable.picasso_02,R.drawable.picasso_03};
		private String descriptions[] = {"Bild 01","Bild 02","Bild_03"};
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView1);
			imageView.setImageResource(imgid[getArguments().getInt(ARG_SECTION_NUMBER)]);
			dummyTextView.setText(descriptions[getArguments().getInt(ARG_SECTION_NUMBER)]);
			return rootView;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == campusCode) {
	    if (data.hasExtra("coords")) {
	    	int[] coords = data.getExtras().getIntArray("coords");
	    	cp.setCurrentCoordinates(coords[0], coords[1]);
	    	
	    	double scX = (double)getResources().getDisplayMetrics().widthPixels;
	    	double scY = (double)getResources().getDisplayMetrics().heightPixels;
	    	
	    	int curr = (cp.getCurrentBuildingByCoordinates(coords[0], coords[1], scX / 800, scY / 600));
	    	
	    	if(curr == 0)
	    		cp.showPicture(R.drawable.hauptgebaeude,hqCode);
	    	else cp.showPicture(R.drawable.campusplan,campusCode);
	    }
	  }
	} 

}
