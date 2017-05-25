package de.gruppe_07.chat.rest.jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

public class HandlerHelper {

	public static boolean authUser(String token, String pseudonym){
		String response = "{'success':false}";
		try{
			Client client = Client.create();
			
			JSONObject request = getRequest(token, pseudonym);
	    	response = client.resource("http://localhost:5001/auth")
	        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
	        .post(String.class, request.toString());
	        
	    	client.destroy();
		} catch(UniformInterfaceException | ClientHandlerException e){}
		
        
        return new JSONObject(response).getBoolean("success");
	}
	
	public static JSONObject getProfile(String token, String pseudonym){
		Client client = Client.create();
		
		JSONObject request = getRequest(token, pseudonym);
    	String response = client.resource("http://localhost:5002/profile")
        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .post(String.class, request.toString());
        
    	client.destroy();
        
        return new JSONObject(response);
	}
	
	public static JSONObject getRequest(String token, String pseudonym){
		JSONObject request = new JSONObject();
    	request.put("token", token);
    	request.put("pseudonym", pseudonym);
    	return request;
	}
	
	public static String formatDate(Date date){
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.format(date);
	}
	
	public static Date parseDate(String date) throws ParseException{
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.parse(date);
	}
}
