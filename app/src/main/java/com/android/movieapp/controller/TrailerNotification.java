package com.android.movieapp.controller;

public interface TrailerNotification 
{
	void onSuccess(Trailer trailerBean);
	void onFail(String erroMessage);
}
