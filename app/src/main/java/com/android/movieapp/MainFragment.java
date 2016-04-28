package com.android.movieapp;

import java.util.ArrayList;
import java.util.List;

import com.android.application.CheckConnection;
import com.android.movieapp.controller.MovieBean;
import com.android.movieapp.controller.MovieNotification;
import com.android.movieapp.controller.MovieSearchController;
import com.android.movieapp.database.DatabaseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainFragment extends Fragment 
{
	private OnMovieSelectedListener listener;
	
	private List<MovieBean> movieList;
	private GridView gridView;
	private MovieGridAdapter adapter;
	
	
	public MainFragment() {
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);
		
		setHasOptionsMenu(true);
		
		
		gridView = (GridView) rootView.findViewById(R.id.gridView_movie);
		
		movieList = new ArrayList<MovieBean>();
		adapter = new MovieGridAdapter(getActivity(), movieList);
		gridView.setAdapter(adapter);
		
		 gridView.setOnItemClickListener(new OnItemClickListener() 
		 {
			 public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
			 {
				 MovieBean movieBean = (MovieBean) adapter.getItem(position);
				 
				 // Fire selected listener event with item
		         listener.onMovieSelected(movieBean); 
			 }
         });
		 
		 //gridView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 
		 parseRequestLink("NowPlaying"); 
	    
		 
		 
		 return rootView;
	}
	
	
	// Initialize the interface listener
	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		    
		if (activity instanceof OnMovieSelectedListener) 
		{
			listener = (OnMovieSelectedListener) activity;
		}
		else 
		{
			throw new ClassCastException(activity.toString()
		              + " must implement ItemsListFragment.OnListItemSelectedListener");
		}
	}
	
	
	
	public void parseRequestLink(String typeOfMovies) 
	{
		String res=CheckConnection.getConnectivityStatusString(getActivity());
		
		 if (res.equals("Wifi enabled") || res.equals("Mobile data enabled")) 
		 {
			 final ProgressDialog pDialog = new ProgressDialog(getActivity());
			 pDialog.setMessage("Loading...");
			 pDialog.setCancelable(false);
			 pDialog.show();
				
				
			 //String typeOfMovies = "NowPlaying";
		    	
			 MovieSearchController movieSearchController = new MovieSearchController(new MovieNotification() 
			 {
				 @Override
				 public void onSuccess(MovieBean movieBean) 
				 {
					 movieList.add(movieBean);
					 adapter.notifyDataSetChanged();
						
					 pDialog.dismiss();
				
				 }
				 
				 @Override
				 public void onFail(String erroMessage) 
				 {
					 pDialog.dismiss();
				 }
			 }, typeOfMovies);
		    	
		    	
		    	
			 movieSearchController.submitRequest();
		 }
		 else if(res.equals("Not connected to Internet"))
		 {
			 Toast toast = Toast.makeText(getActivity(),getResources().getString(R.string.no_network), Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.CENTER, 0, 0);
			 toast.show();
		 }
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
            case R.id.action_settings:
                return true; 
            
            case R.id.action_settings_popular:
                
            	item.setChecked(true);
            	
            	movieList = new ArrayList<MovieBean>();
        		adapter = new MovieGridAdapter(getActivity(), movieList);
        		gridView.setAdapter(adapter);
            	parseRequestLink("Popular");
            	
                return true;
            case R.id.action_settings_topRated:
                
            	item.setChecked(true);
            	
            	movieList = new ArrayList<MovieBean>();
        		adapter = new MovieGridAdapter(getActivity(), movieList);
        		gridView.setAdapter(adapter);
            	parseRequestLink("TopRated");
            	
                return true;
             
            case R.id.action_settings_favourite:

            	DatabaseHandler db = new DatabaseHandler(getActivity());
            	int numberOfMovies = db.getMoviesCount();
        		if(numberOfMovies != 0)
        		{
        			item.setChecked(true);
        			//Toast.makeText(getActivity(), "Favourite list has "+numberOfMovies+" items", Toast.LENGTH_LONG).show();
        			
        			movieList = new ArrayList<MovieBean>();
        			movieList = db.getAllMovies();  
            		adapter = new MovieGridAdapter(getActivity(), movieList);
            		gridView.setAdapter(adapter);
        		}
        		else
        		{
        			Toast.makeText(getActivity(), "Favourite list is empty", Toast.LENGTH_LONG).show();
        		}
        		
        		return true;
                
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}
