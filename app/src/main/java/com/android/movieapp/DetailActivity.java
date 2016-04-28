package com.android.movieapp;

import com.android.movieapp.controller.MovieBean;

import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class DetailActivity extends FragmentActivity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// if it comes from intent (mobile case)
		MovieBean movieItem = (MovieBean) getIntent().getSerializableExtra("MovieDetailsKey");
	
		if (savedInstanceState == null) 
		{
			// Insert detail fragment based on the item passed
		    DetailFragment detailFragment = DetailFragment.newInstance(movieItem);
	        FragmentManager fragmentManager = getSupportFragmentManager();
	        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	        fragmentTransaction.replace(R.id.detail_container, detailFragment);
	        fragmentTransaction.addToBackStack(null);
	        fragmentTransaction.commit();
		}
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
	  if (id == android.R.id.home){
		  getUserSelection();
      	 return true;
	  }
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onBackPressed() {
		getUserSelection();
	}

	
	private void getUserSelection() {
		
		finish();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	
		
	}

	
}
