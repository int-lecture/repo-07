package de.gruppe_07.rest.jersey;

import java.util.ArrayList;

public class User {

	private static ArrayList<User> userList = new ArrayList<User>();
	
	private String name;
	private int sequence;
	private ArrayList<Message> messageList;
	
	public User(){
		this.name = "";
		this.sequence = 1;
		this.messageList = new ArrayList<Message>();
		
	}
	
	public User(String name){
		userList.add(this);
		this.name = name;
		this.sequence = 1;
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
		for(int i = 0; i < userList.size(); i++){
			if(userList.get(i).getName().equals(username))
				return userList.get(i);
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
		if(sequence == 0)
			return messageList;
		ArrayList<Message> temp = new ArrayList<Message>();
		for(int i = 0; i < messageList.size(); i++){
			if(messageList.get(i).getSequence() > sequence)
				temp.add(messageList.get(i));
		}
		this.messageList = temp;
		return temp;
	}
	
	public void deleteMessages(int sequence){
		ArrayList<Message> temp = new ArrayList<Message>();
		for(int i = 0; i < messageList.size();i++){
			if(messageList.get(i).getSequence() > sequence)
				temp.add(messageList.get(i));
		}
		messageList = temp;
	}
}