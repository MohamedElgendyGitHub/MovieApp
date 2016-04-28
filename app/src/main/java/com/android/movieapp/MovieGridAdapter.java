package com.android.movieapp;

import java.util.List;

import com.android.movieapp.controller.MovieBean;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;



public class MovieGridAdapter extends BaseAdapter
{
    private Activity activity;
    private LayoutInflater inflater;
    private List<MovieBean> moviesItems;
 
    public MovieGridAdapter(Activity activity, List<MovieBean> moviesItems) 
    {
		this.activity = activity;
		this.moviesItems = moviesItems;
	}
 
    @Override
    public int getCount() {
        return moviesItems.size();
    }
 
    @Override
    public Object getItem(int position) {
        return moviesItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
    	
    	ViewHolder holder;
 
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        if (convertView == null)
        {
        	convertView = inflater.inflate(R.layout.gird_row, null);
        	holder = new ViewHolder();
        	
            holder.moviePosterImage = (ImageView) convertView.findViewById(R.id.imageView_movieImage); 

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
            
        
        
        // filling the Grid
        MovieBean movieBean = (MovieBean) getItem(position);
        //"w92", "w154", "w185", "w342", "w500", "w780", or "original"
        String sizeOfImage = "w185";
        
        
        String moviePosterURL = "http://image.tmdb.org/t/p/"+sizeOfImage+"/"+movieBean.getPosterPath();
        //String moviePosterURL = http://image.tmdb.org/t/p/w185//6bCplVkhowCjTHXWv49UjRPn0eK.jpg
        
        Picasso.with(activity).load(moviePosterURL).into(holder.moviePosterImage);

 
        return convertView;
    }
    
    
    
    static class ViewHolder 
    {
    	private ImageView moviePosterImage;
    }
 
}