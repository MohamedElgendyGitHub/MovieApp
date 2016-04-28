package com.android.movieapp.controller;

public interface MovieNotification 
{
	void onSuccess(MovieBean movieBean);
	void onFail(String erroMessage);
}
