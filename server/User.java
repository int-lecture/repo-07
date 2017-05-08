package com.example.rest;

import java.util.ArrayList;

public class User {

	private static ArrayList<User> userList = new ArrayList<User>();
	
	private String name;
	private int sequence;
	private ArrayList<Message> messageList;
	
	public User(){
		this.name = "";
		this.sequence = 0;
		this.messageList = new ArrayList<Message>();
		
	}
	
	public User(String name){
		userList.add(this);
		this.name = name;
		this.sequence = 0;
		this.messageList = new ArrayList<Message>();
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getSequence(){
		return sequence;
	}
	
	public void setSequence(int sequence){
		this.sequence = sequence;
	}
	
	public static User getUser(String username){
		int i = 0;
		while(i < userList.size()){
			if(userList.get(i).getName().equals(username))
				return userList.get(i);
			i++;
		}		
		return null;
	}
	
	public ArrayList<Message> getMessageList(){
		return messageList;
	}
	
	public void saveMessage(Message message){
		message.setSequence(this.sequence);
		messageList.add(message);
		this.sequence++;
	}
	
	public ArrayList<Message> getMessages(int sequence){
		ArrayList<Message> temp = new ArrayList<Message>();
		int amountMessages;
		if(sequence == 0)
			amountMessages = messageList.size();
		else 
			amountMessages = this.sequence - sequence - 2;
		
		for(int i = 0; i < amountMessages; i++){
			temp.add(messageList.get(i));
		}
		messageList.clear();
		return temp;
	}
	
	public void deleteMessages(){
		messageList.clear();
	}
}
