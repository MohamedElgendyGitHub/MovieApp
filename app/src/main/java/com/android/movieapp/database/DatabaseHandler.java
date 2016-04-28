package com.android.movieapp.database;

import java.util.ArrayList;
import java.util.List;

import com.android.movieapp.controller.MovieBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper 
{

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "moviesDB";

	// Movies table name
	private static final String TABLE_MOVIES = "movies";

	// Movie Table Columns names
	public static final String MOVIE_ID = "_id";
	public static final String MOVIE_TITLE = "_title";
	public static final String MOVIE_OVERVIEW = "_overview";
	public static final String MOVIE_RATING = "_rating";
	public static final String MOVIE_RELEASE_DATE = "_releasedate";
	public static final String MOVIE_POSTER = "_poster";

	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
				+ MOVIE_ID + " TEXT primary key," + 
				MOVIE_TITLE + " TEXT," +
				MOVIE_OVERVIEW + " TEXT," +
				MOVIE_RATING + " TEXT," +
				MOVIE_RELEASE_DATE + " TEXT," +
				MOVIE_POSTER + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new movie
	public void addMovie(MovieBean movie) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MOVIE_ID, movie.getId()); // Movie id
		values.put(MOVIE_TITLE, movie.getTitle()); // Movie Title
		values.put(MOVIE_OVERVIEW, movie.getOverView()); // Movie Overview
		values.put(MOVIE_RATING, movie.getVoteAverage()); // Movie Vote count
		values.put(MOVIE_RELEASE_DATE, movie.getReleaseDate()); // Movie Release date
		values.put(MOVIE_POSTER, movie.getPosterPath()); // Movie Poster

		// Inserting Row
		db.insert(TABLE_MOVIES, null, values);
		db.close(); // Closing database connection
	}

	// Getting single Movie
	MovieBean getMovie(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MOVIES, new String[] { MOVIE_ID,
				MOVIE_TITLE, MOVIE_OVERVIEW , MOVIE_RATING , MOVIE_RELEASE_DATE , MOVIE_POSTER }, MOVIE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		MovieBean movie = new MovieBean();
		movie.setId(cursor.getString(0));
		movie.setTitle(cursor.getString(1));
		movie.setOverView(cursor.getString(2));
		movie.setVoteAverage(Float.parseFloat(cursor.getString(3)));
		movie.setReleaseDate(cursor.getString(4));
		movie.setPosterPath(cursor.getString(5));

		// return movie
		return movie;
	}

	// Getting All Movies
	public List<MovieBean> getAllMovies() {
		List<MovieBean> movieList = new ArrayList<MovieBean>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				MovieBean movie = new MovieBean();
				
				movie.setId(cursor.getString(0));
				movie.setTitle(cursor.getString(1));
				movie.setOverView(cursor.getString(2));
				movie.setVoteAverage(Float.parseFloat(cursor.getString(3)));
				movie.setReleaseDate(cursor.getString(4));
				movie.setPosterPath(cursor.getString(5));
			
				// Adding movie to list
				movieList.add(movie);
			} while (cursor.moveToNext());
		}

		// return movie list
		return movieList;
	}

	// Updating single movie
	public int updateMovie(MovieBean movie) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MOVIE_TITLE, movie.getTitle()); // Movie Title
		values.put(MOVIE_OVERVIEW, movie.getOverView()); // Movie Overview
		values.put(MOVIE_RATING, movie.getVoteAverage()); // Movie Vote count
		values.put(MOVIE_RELEASE_DATE, movie.getReleaseDate()); // Movie Release date
		values.put(MOVIE_POSTER, movie.getPosterPath()); // Movie Poster

		// updating row
		return db.update(TABLE_MOVIES, values, MOVIE_ID + " = ?",
				new String[] { String.valueOf(movie.getId()) });
	}

	// Deleting single movie
	public void deleteMovie(MovieBean movie) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MOVIES, MOVIE_ID + " = ?",
				new String[] { String.valueOf(movie.getId()) });
		db.close();
	}

	// Getting movies Count
	public int getMoviesCount() {

		String countQuery = "SELECT  * FROM " + TABLE_MOVIES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		

		// return count
		return cursor.getCount();
	}

}