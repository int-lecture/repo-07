package de.gruppe_07.rest.jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

	private String from;
	private String to;
	private Date date;
	private String text;
	private int sequence;
	
	public Message(){
		this.from ="";
		this.to = "";
		this.date = new Date(0,0,0,0,0,0);
		this.text = "";
		this.sequence = -1;
	}
	
	public Message(String from, String to, Date date, String text){
		this.from = from;
		this.to = to;
		this.date = date;
		this.text = text;
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
}
