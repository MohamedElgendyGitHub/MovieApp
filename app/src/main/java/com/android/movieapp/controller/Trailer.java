package com.android.movieapp.controller;

public class Trailer
{

	String id;
	String key; //this will be used
	String name; // this will be used 
	String site; // this will be used
	int size;
	String type;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String generateTrailerURL()
	{
		String generatedURL =  "https://www.youtube.com/watch?v="+getKey();
		return generatedURL;
	}
	
	
	
}
