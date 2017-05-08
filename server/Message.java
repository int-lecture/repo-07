package com.example.rest;

import java.util.Date;

public class Message {

	private String from;
	private String to;
	private Date date;
	private String text;
	private int sequence;
	
	public Message(){
		this.setFrom("");
		this.setTo("");
		this.setDate(new Date(0,0,0,0,0,0));
		this.setText("");
		this.sequence = -1;
	}
	
	public Message(String from, String to, Date date, String text){
		this.setFrom(from);
		this.setTo(to);
		this.setDate(date);
		this.setText(text);
		this.sequence = -1;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public boolean complete(){
		if(!from.equals("") && !to.equals("") && !date.equals(new Date(0,0,0,0,0,0)) && !text.equals(""))
			return true;
		return false;
	}
	
}
