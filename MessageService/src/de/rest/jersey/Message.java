package de.rest.jersey;

public class Message {

	String from;
	String to;
	String date;
	String text;
	long sequence;
	
	public Message(){
		this.from = "";
		this.to = "";
		this.date = "";
		this.text = "";
		this.sequence = -1L;
	}
	
	public Message(String from, String to, String date, String text, long sequence){
		this.from = from;
		this.to = to;
		this.date = date;
		this.text = text;
		this.sequence = sequence;
	}
}
