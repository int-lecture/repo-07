package de.gruppe_07.rest.jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;

@Path("send")
public class SendHandler{
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putMessage(String input) throws JSONException, ParseException{
		JSONObject messageJson = new JSONObject(input);
		if(messageComplete(messageJson)){
			User user = User.getUser(messageJson.getString("to"));
			if(user == null)
				user = new User(messageJson.getString("to"));
			if(user.validUser(messageJson.getString("token"), messageJson.getString("from"))){
				Message message = new Message(messageJson.getString("from"), messageJson.getString("to"), user.parseDate(messageJson.getString("date")), messageJson.getString("text"));
				user.saveMessage(message);
				JSONObject response = new JSONObject();
				response.put("date", user.formatDate(new Date()));
				response.put("sequence", user.getSequence()-1);
				return Response.status(201).entity(response.toString()).build();
			}
			return Response.status(401).build();
		}
		return Response.status(400).build();
	}
	
	public boolean messageComplete(JSONObject message){
		if(message.has("token") && message.has("from") && message.has("to") && message.has("date") && message.has("text"))
			return true;
		return false;
	}
}
