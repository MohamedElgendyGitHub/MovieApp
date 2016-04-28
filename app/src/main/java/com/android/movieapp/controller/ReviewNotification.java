package com.android.movieapp.controller;

public interface ReviewNotification
{
	void onSuccess(Review reviewBean);
	void onFail(String erroMessage);
}
