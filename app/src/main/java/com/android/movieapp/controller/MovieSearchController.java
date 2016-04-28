package com.android.movieapp.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.application.AppController;
import com.android.application.MovieAppUtils;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

public class MovieSearchController 
{
	private static String movieApiUrlRequest;
	private MovieNotification notificationCallback;
	private TrailerNotification trailerNotificationCallback;
	private ReviewNotification reviewNotificationCallback;
	
	// JSON Node Names
	private static final String TAG_MAIN_RESULT_NODE = "results";
	private static final String TAG_POSTER_Path = "poster_path";
	private static final String TAG_ADULT = "adult";
	private static final String TAG_OVERVIEW = "overview";
	private static final String TAG_RELEASE_DATE = "release_date";
	private static final String TAG_ID = "id";
	private static final String TAG_ORIGINAL_TITLE = "original_title";
	private static final String TAG_ORIGINAL_LAGNUAGE = "original_language";
	private static final String TAG_TITLE = "title";
	private static final String TAG_BACKDROP_PATH = "backdrop_path";
	private static final String TAG_POPULARITY = "popularity";
	private static final String TAG_VOTE_COUNT = "vote_count";
	private static final String TAG_VIDEO = "video";
	private static final String TAG_VOTE_AVERAGE = "vote_average";
	
	//Trailer Response
	private static final String TAG_TRAILER_ID = "id";
	private static final String TAG_TRAILER_KEY = "key";
	private static final String TAG_TRAILER_NAME = "name";
	private static final String TAG_TRAILER_SITE = "site";
	private static final String TAG_TRAILER_SIZE = "size";
	private static final String TAG_TRAILER_TYPE = "type";
	
	//Review Response
	private static final String TAG_REVIEW_ID = "id";
	private static final String TAG_REVIEW_AUTHOR = "author";
	private static final String TAG_REVIEW_CONTENT = "content";
	private static final String TAG_REVIEW_URL = "url";
	
	
	public MovieSearchController(TrailerNotification trailerNotificationCallback) 
	{
		this.trailerNotificationCallback = trailerNotificationCallback;
	}
	
	
	public MovieSearchController(ReviewNotification reviewNotificationCallback) 
	{
		this.reviewNotificationCallback = reviewNotificationCallback;
	}
	
	
	public MovieSearchController(MovieNotification notificationCallback , String typeOfMovies) 
	{
		if(typeOfMovies.equals("Popular"))
		{
			movieApiUrlRequest = MovieAppUtils.requestPopularMovies;
		}
		else if(typeOfMovies.equals("TopRated"))
		{
			movieApiUrlRequest = MovieAppUtils.requestTopRatedMovies;
		}
		else
		{
			movieApiUrlRequest = MovieAppUtils.requestNowPlayingMovies;
		}
		
		
		this.notificationCallback = notificationCallback;
	}
	
	
	public void submitRequest()
	{
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,movieApiUrlRequest, null, new Response.Listener<JSONObject>() 
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						try 
						{
							JSONArray listJsonArray = response.getJSONArray(TAG_MAIN_RESULT_NODE);
							for (int i = 0; i < listJsonArray.length(); i++) 
							{
								JSONObject jsonObject = listJsonArray.getJSONObject(i);
								
								
								String posterPath = jsonObject.optString(TAG_POSTER_Path).toString();
								String isAdult = jsonObject.optString(TAG_ADULT).toString();
								String overView = jsonObject.optString(TAG_OVERVIEW).toString();
								String releaseDate = jsonObject.optString(TAG_RELEASE_DATE).toString();
								String id = jsonObject.optString(TAG_ID).toString();
								String originalTitle = jsonObject.optString(TAG_ORIGINAL_TITLE).toString();
								String originalLanguage = jsonObject.optString(TAG_ORIGINAL_LAGNUAGE).toString();
								String title = jsonObject.optString(TAG_TITLE).toString();
								String backDropPath = jsonObject.optString(TAG_BACKDROP_PATH).toString();
								String popularity = jsonObject.optString(TAG_POPULARITY).toString();
								String voteCount = jsonObject.optString(TAG_VOTE_COUNT).toString();
								String hasVideo = jsonObject.optString(TAG_VIDEO).toString();
								String voteAverage = jsonObject.optString(TAG_VOTE_AVERAGE).toString();
								
								
								
								
								MovieBean movieBean = new MovieBean();
								movieBean.setPosterPath(posterPath);
								
								movieBean.setAdult(Boolean.valueOf(isAdult));
								
								movieBean.setOverView(overView);
								movieBean.setReleaseDate(releaseDate);
								movieBean.setId(id);
								movieBean.setOriginalTitle(originalTitle);
								movieBean.setOriginalLanguage(originalLanguage);
								movieBean.setTitle(title);
								movieBean.setBackDropPath(backDropPath);
								
								movieBean.setPopularity(Float.parseFloat(popularity));
								movieBean.setVoteCount(Integer.parseInt(voteCount));
								movieBean.setHasVideo(Boolean.valueOf(hasVideo));
								movieBean.setVoteAverage(Float.parseFloat(voteAverage));
								
								
								
									
								notificationCallback.onSuccess(movieBean);
							}
						}
						catch (JSONException e) 
						{
								e.printStackTrace();
								notificationCallback.onFail(e.getMessage());
						}
						
					}
				},new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						notificationCallback.onFail(error.getMessage());
						}
					});

		
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjReq);
		}
	
	
	
	public void submitTrailerRequest(String id)
	{
		
		String url = MovieAppUtils.getTrailerUrl(id);
		
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,url, null, new Response.Listener<JSONObject>() 
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						try 
						{
							JSONArray listJsonArray = response.getJSONArray(TAG_MAIN_RESULT_NODE);
							
							System.out.println("size of Trailers :"+listJsonArray.length());
							
							for (int i = 0; i < listJsonArray.length(); i++) 
							{
								JSONObject jsonObject = listJsonArray.getJSONObject(i);
								
								
								String id = jsonObject.optString(TAG_TRAILER_ID).toString();
								String key = jsonObject.optString(TAG_TRAILER_KEY).toString();
								String name = jsonObject.optString(TAG_TRAILER_NAME).toString();
								String site = jsonObject.optString(TAG_TRAILER_SITE).toString();
								String size = jsonObject.optString(TAG_TRAILER_SIZE).toString();
								String type = jsonObject.optString(TAG_TRAILER_TYPE).toString();
								
								
								
								Trailer movieTrailer = new Trailer();
								
								movieTrailer.setId(id);
								movieTrailer.setKey(key);
								movieTrailer.setName(name);
								movieTrailer.setSite(site);
								movieTrailer.setSize(Integer.parseInt(size));
								movieTrailer.setType(type);
								
									
								trailerNotificationCallback.onSuccess(movieTrailer);
							}
						}
						catch (JSONException e) 
						{
								e.printStackTrace();
								trailerNotificationCallback.onFail(e.getMessage());
						}
						
					}
				},new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						trailerNotificationCallback.onFail(error.getMessage());
						}
					});

		
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjReq);
		}
	
	
	public void submitReviewRequest(String id)
	{
		
		String url = MovieAppUtils.getReviewUrl(id);
		
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,url, null, new Response.Listener<JSONObject>() 
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						try 
						{
							JSONArray listJsonArray = response.getJSONArray(TAG_MAIN_RESULT_NODE);
							
							System.out.println("size of Reviews :"+listJsonArray.length());
							
							for (int i = 0; i < listJsonArray.length(); i++) 
							{
								JSONObject jsonObject = listJsonArray.getJSONObject(i);
								
								
								String id = jsonObject.optString(TAG_REVIEW_ID).toString();
								String author = jsonObject.optString(TAG_REVIEW_AUTHOR).toString();
								String content = jsonObject.optString(TAG_REVIEW_CONTENT).toString();
								String url = jsonObject.optString(TAG_REVIEW_URL).toString();
							
								
								Review movieReview = new Review();
								
								movieReview.setId(id);
								movieReview.setAuthor(author);
								movieReview.setContent(content);
								movieReview.setUrl(url);
								
									
								reviewNotificationCallback.onSuccess(movieReview);
							}
						}
						catch (JSONException e) 
						{
								e.printStackTrace();
								reviewNotificationCallback.onFail(e.getMessage());
						}
						
					}
				},new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						reviewNotificationCallback.onFail(error.getMessage());
						}
					});

		
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjReq);
		}
	
}
