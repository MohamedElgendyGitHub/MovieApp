package com.android.movieapp;

import java.util.ArrayList;

import com.android.movieapp.controller.MovieBean;
import com.android.movieapp.controller.MovieSearchController;
import com.android.movieapp.controller.Review;
import com.android.movieapp.controller.ReviewNotification;
import com.android.movieapp.controller.Trailer;
import com.android.movieapp.controller.TrailerNotification;
import com.android.movieapp.database.DatabaseHandler;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailFragment extends Fragment 
{
	LinearLayout detailLayout;
	LayoutInflater layoutInflater;
	boolean firstTrailerResult = false;
	boolean firstReviewResult = false;
	
	MovieBean movieItem;
	ImageView moviePoster;
	TextView movieTitle;
	TextView movieOverView;
	TextView movieRating;
	TextView movieReleaseDate;
	Button markAsFavouriteButton;
	
	ArrayList<Trailer> trailersList;
	ArrayList<Review> reviewList;
	

	private DatabaseHandler db;
	boolean isMarked = false;
	
	
	public static DetailFragment newInstance(MovieBean movieItem) 
	{
		DetailFragment detailFragment = new DetailFragment();
	    Bundle args = new Bundle();
	    args.putSerializable("MovieDetailsKey", movieItem);
	    detailFragment.setArguments(args);
	    return detailFragment;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  movieItem = (MovieBean) getArguments().getSerializable("MovieDetailsKey");
	}
	
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_detail,container, false);
		
		
		findViewsById(rootView);
		setViews(movieItem);
		
		
		db = new DatabaseHandler(getActivity()); 
		
		ArrayList<MovieBean> checkList = new  ArrayList<MovieBean>();
		checkList = (ArrayList<MovieBean>) db.getAllMovies();
		
		for(MovieBean movie : checkList)
		{
			if(movie.getId().equals(movieItem.getId()))
			{
				isMarked = true;
				markAsFavouriteButton.setBackgroundResource(R.drawable.mark);
				break;
			}
		}
		
		
        markAsFavouriteButton.setOnClickListener(new OnClickListener() 
        {
        	@Override
			public void onClick(View v) 
			{
        		
        		if(isMarked)
        		{
        			/*db.deleteMovie(movieItem);
        			
        			isMarked = false;
        			markAsFavouriteButton.setBackgroundResource(R.drawable.unmark);*/
        		}
        		else
        		{
        			db.addMovie(movieItem);
        			
        			isMarked = true;
        			markAsFavouriteButton.setBackgroundResource(R.drawable.mark);
        		}        		
			}
		});
        
        parseTrailers(movieItem.getId());
        parseReviews(movieItem.getId());
        
		
		return rootView;
	}
	
	
	
	private void findViewsById(View rootView)
	{
		detailLayout = (LinearLayout) rootView.findViewById(R.id.detail_layout);
		layoutInflater =  (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		moviePoster = (ImageView) rootView.findViewById(R.id.imageView_detail_poster);
		movieTitle = (TextView) rootView.findViewById(R.id.textView_detail_title);
		movieOverView = (TextView) rootView.findViewById(R.id.textView_detail_overView);
		movieRating = (TextView) rootView.findViewById(R.id.textView_detail_rating);
		movieReleaseDate = (TextView) rootView.findViewById(R.id.textView_detail_releaseDate);
		markAsFavouriteButton = (Button) rootView.findViewById(R.id.button_detail_mark_as_favourite);

	}
	
	private void setViews(MovieBean movieBean) 
	{
		movieTitle.setText("\t "+movieBean.getTitle()+" \t");
        movieOverView.setText(movieBean.getOverView());
        movieRating.setText(movieBean.getVoteAverage()+"");
        movieReleaseDate.setText(movieBean.getReleaseDate());
        

		//"w92", "w154", "w185", "w342", "w500", "w780", or "original"
        //String moviePosterURL = http://image.tmdb.org/t/p/w185//6bCplVkhowCjTHXWv49UjRPn0eK.jpg
        String sizeOfImage = "w342";
        String moviePosterURL = "http://image.tmdb.org/t/p/"+sizeOfImage+"/"+movieBean.getPosterPath();
        Picasso.with(getActivity()).load(moviePosterURL).into(moviePoster);
	}


	public void parseTrailers(String id) 
	{
		/*
		final ProgressDialog pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		pDialog.show();
		*/
		
		trailersList = new ArrayList<Trailer>();
		
    	MovieSearchController movieSearchController = new MovieSearchController(new TrailerNotification() {
			
			@Override
			public void onSuccess(final Trailer trailerBean) {
				//System.out.println("Trailer :"+trailerBean.getKey());
				trailersList.add(trailerBean);
				
				View trailer_row = layoutInflater.inflate(R.layout.trailer_row, null);
				
				TextView trailerLabel = (TextView) trailer_row.findViewById(R.id.textView_trailer);
				LinearLayout trailer = (LinearLayout) trailer_row.findViewById(R.id.trailer_header_layout);
				TextView trailerTitle = (TextView) trailer_row.findViewById(R.id.textView_trailer_title);
				
				if(firstTrailerResult == false)
				{
					firstTrailerResult = true;
					trailerLabel.setTextColor(Color.BLACK);
				}
				else
				{
					trailerLabel.setVisibility(View.INVISIBLE);
				}
					
				trailerTitle.setText(trailerBean.getName());
				trailerTitle.setTypeface(null, Typeface.ITALIC);
				trailerTitle.setTextColor(getResources().getColor(R.color.mygray));
				
				
				trailer.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerBean.generateTrailerURL()));
				        startActivity(intent);
					}
				});
				
				
				
				detailLayout.addView(trailer_row);
				
				//pDialog.dismiss();
			}
			
			@Override
			public void onFail(String erroMessage) {
				//pDialog.dismiss();
			}
		});
    	
		movieSearchController.submitTrailerRequest(id);
	}
	
	
	
	public void parseReviews(String id) 
	{
		/*
		final ProgressDialog pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		pDialog.show();
		*/
	
		reviewList = new ArrayList<Review>();
		
    	MovieSearchController movieSearchController = new MovieSearchController(new ReviewNotification() {
			
			@Override
			public void onSuccess(Review reviewBean) {
				//System.out.println("Review :"+reviewBean.getAuthor());
				reviewList.add(reviewBean);
				
				View review_row = layoutInflater.inflate(R.layout.review_row, null);
				
				TextView reviewLabel = (TextView) review_row.findViewById(R.id.textView_reveiw);
				TextView authorName = (TextView) review_row.findViewById(R.id.textView_review_author);
				TextView reviewContent = (TextView) review_row.findViewById(R.id.textView_review_content);
				
				if(firstReviewResult == false)
				{
					firstReviewResult = true;
					reviewLabel.setTextColor(Color.BLACK);
				}
				else
				{
					reviewLabel.setVisibility(View.INVISIBLE);
				}
				
				authorName.setText(reviewBean.getAuthor());
				authorName.setTextColor(Color.BLACK);
				reviewContent.setText(reviewBean.getContent());
				
				detailLayout.addView(review_row);
				
				//pDialog.dismiss();
			}
			
			@Override
			public void onFail(String erroMessage) {
				//pDialog.dismiss();
			}
		});
    	
		movieSearchController.submitReviewRequest(id);
	}
	
	
	
}
