package com.android.application;

public class MovieAppUtils 
{
	
	// for movies api request
	private final static String movieAPIKey = "ef0fd15ea0fa078fcaf348b815ae8844";
	private final static String movieBaseUrl = "http://api.themoviedb.org/3/movie/";
	public final static String requestNowPlayingMovies = movieBaseUrl+"now_playing?api_key="+movieAPIKey;
	public final static String requestPopularMovies = movieBaseUrl+"popular?api_key="+movieAPIKey;
	public final static String requestTopRatedMovies = movieBaseUrl+"top_rated?api_key="+movieAPIKey;
	
	
	public static String getTrailerUrl(String id)
	{
		String url = movieBaseUrl + id + "/videos?api_key="+movieAPIKey;
		return url;
	}
	
	public static String getReviewUrl(String id)
	{
		String url = movieBaseUrl + id + "/reviews?api_key="+movieAPIKey;
		return url; 
	}
}
