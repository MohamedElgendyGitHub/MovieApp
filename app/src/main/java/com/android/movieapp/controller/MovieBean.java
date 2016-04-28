package com.android.movieapp.controller;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieBean implements Serializable
{

	/**
	 * 
	*/
	private static final long serialVersionUID = 1L;
	
	
	private String posterPath;
	private boolean isAdult;
	private String overView;
	private String releaseDate;
	private String id;
	private String originalTitle;
	private String originalLanguage;
	private String title;
	private String backDropPath;
	private float popularity;
	private int voteCount;
	private boolean hasVideo;
	private float voteAverage;
	private ArrayList<Review> reviews;
	private ArrayList<Trailer> trailers;
	
	
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	public boolean isAdult() {
		return isAdult;
	}
	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}
	public String getOverView() {
		return overView;
	}
	public void setOverView(String overView) {
		this.overView = overView;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOriginalTitle() {
		return originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	public String getOriginalLanguage() {
		return originalLanguage;
	}
	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBackDropPath() {
		return backDropPath;
	}
	public void setBackDropPath(String backDropPath) {
		this.backDropPath = backDropPath;
	}
	public float getPopularity() {
		return popularity;
	}
	public void setPopularity(float popularity) {
		this.popularity = popularity;
	}
	public int getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	public boolean isHasVideo() {
		return hasVideo;
	}
	public void setHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
	}
	public float getVoteAverage() {
		return voteAverage;
	}
	public void setVoteAverage(float voteAverage) {
		this.voteAverage = voteAverage;
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	public ArrayList<Trailer> getTrailers() {
		return trailers;
	}
	public void setTrailers(ArrayList<Trailer> trailers) {
		this.trailers = trailers;
	}
	
	
}