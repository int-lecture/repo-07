package de.gruppe_07.chat.rest.jersey;

import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("send")
public class SendHandler {
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putMessage(String messageString) throws JSONException, ParseException, org.codehaus.jettison.json.JSONException{
		JSONObject messageJson = new JSONObject(messageString);
		
		if(messageJson.has("token") && messageJson.has("from") && messageJson.has("to") && messageJson.has("date") && messageJson.has("text")){
			String token = messageJson.getString("token");
			String pseudonym = messageJson.getString("from");
			
			if(HandlerHelper.authUser(token, pseudonym)){
				StorageProviderMongoDB mongoDB = new StorageProviderMongoDB();
				String to = messageJson.getString("to"); 
				String date = messageJson.getString("date");
				String text = messageJson.getString("text");
				
				Long sequence = mongoDB.retrieveAndUpdateSequence(to);

				Message message = new Message(pseudonym, to, date, text, sequence);
				mongoDB.storeMessage(message);
				
				JSONObject response = new JSONObject();
				response.put("date", HandlerHelper.formatDate(new Date()));
				response.put("sequence", sequence);
				
				return Response.status(201).entity(response.toString()).build();
			}
			
			return Response.status(401).build();
		}
		
		return Response.status(400).build();
	}
}
