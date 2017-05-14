package de.gruppe_07.rest.jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;

public class User {

	private static ArrayList<User> userList = new ArrayList<User>();
	
	private String token;
	private String name;
	private int sequence;
	private ArrayList<Message> messageList;
	
	public User(){
		this.token ="";
		this.name = "";
		this.sequence = 1;
		this.messageList = new ArrayList<Message>();
		
	}
	
	public User(String name){
		userList.add(this);
		this.token = "";
		this.name = name;
		this.sequence = 1;
		this.messageList = new ArrayList<Message>();
	
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
	
	public boolean validUser(String token, String pseudonym) throws JSONException, ParseException{
		Client client = Client.create();
		JSONObject body = new JSONObject();
		body.put("token", token);
		body.put("pseudonym", pseudonym);
        JSONObject result = client.resource("http://localhost:5001/auth").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(JSONObject.class, body);
        
        if(result.getBoolean("success") && parseDate(result.getString("expire-date")).before(new Date()))
        	return true;
        return false;
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
	
	public String formatDate(Date date){
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.format(date);
	}
	
	public Date parseDate(String date) throws ParseException{
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.parse(date);
	}
}