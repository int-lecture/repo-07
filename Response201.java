package com.example.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Response201 {
	
	private String date;
	private int sequence;
	
	public Response201(){
	}
	
	public Response201(int sequence){
		this.date = getDate();
		this.sequence = sequence;
	}
	
	public String getDate(){
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.format(new Date());
	}
	
	public int getSequence(){
		return this.sequence;
	}
	
	public void setSequence(){
		this.sequence = sequence;
	}
}
