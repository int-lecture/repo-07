package de.rest.jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;

public class Handler {

	public boolean authUser(String token, String pseudonym){
		Client client = Client.create();
		
		JSONObject request = getRequest(token, pseudonym);
    	String response = client.resource("http://141.19.142.61:5001/auth")
        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .post(String.class, request.toString());
        
    	client.destroy();
        
        return new JSONObject(response).getBoolean("success");
	}
	
	public JSONObject getProfile(String token, String pseudonym){
		Client client = Client.create();
		
		JSONObject request = getRequest(token, pseudonym);
    	String response = client.resource("http://141.19.142.61:5002/profile")
        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .post(String.class, request.toString());
        
    	client.destroy();
        
        return new JSONObject(response);
	}
	
	public JSONObject getRequest(String token, String pseudonym){
		JSONObject request = new JSONObject();
    	request.put("token", token);
    	request.put("pseudonym", pseudonym);
    	return request;
	}
	
	public String formatDate(Date date){
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.format(date);
	}
	
	public Date parseDate(String date) throws ParseException{
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.parse(date);
	}
}
