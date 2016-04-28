package com.android.movieapp;

import com.android.movieapp.controller.MovieBean;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity implements OnMovieSelectedListener
{
	// Flag determines if this is a one or two pane layout
	private boolean isTwoPane = false;
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Call this to determine which layout we are in (tablet or phone)
	    determinePaneLayout();
	
		
		if (savedInstanceState == null) 
		{
			Fragment fragment = new MainFragment();
	        FragmentManager fragmentManager = getSupportFragmentManager();
	        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	        fragmentTransaction.replace(R.id.container, fragment);
	        fragmentTransaction.addToBackStack(null);
	        fragmentTransaction.commit();
		}
	}
	
	
	private void determinePaneLayout()
	{
		LinearLayout fragmentItemDetail = (LinearLayout) findViewById(R.id.detail_container);
	    // If there is a second pane for details
	    if (fragmentItemDetail != null) {
	      isTwoPane = true;
	    }
	}

	@Override
	public void onMovieSelected(MovieBean movieItem) 
	{
		if (isTwoPane) 
		{
			// single activity with list and detail
		    // Replace framelayout with new detail fragment
	        DetailFragment detailFragment = DetailFragment.newInstance(movieItem);
	        FragmentManager fragmentManager = getSupportFragmentManager();
	        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	        fragmentTransaction.replace(R.id.detail_container, detailFragment);
	        fragmentTransaction.addToBackStack(null);
	        fragmentTransaction.commit();
		}
		else 
		{ 
			// go to separate activity
			// For phone, launch detail activity using intent
			Intent showDetails = new Intent(MainActivity.this, DetailActivity.class);
	        showDetails.putExtra("MovieDetailsKey",movieItem);
	        startActivity(showDetails);
	        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	
}
