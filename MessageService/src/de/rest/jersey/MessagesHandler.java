package de.rest.jersey;

import java.text.ParseException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.google.gson.Gson;

@Path("/messages/{user_id}")
public class MessagesHandler extends Handler{
	
	@Path("/{sequence_number}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sequenceAvailable(@Context HttpHeaders tokenHeader, @PathParam("user_id") String pseudonym, @PathParam("sequence_number") int sequence_number) throws JSONException, ParseException{
		String token = tokenHeader.getRequestHeader("Authorization").get(0).substring(6);
		return getMessages(token, pseudonym, sequence_number);
	}
		
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sequenceNotAvailable(@Context HttpHeaders tokenHeader, @PathParam("user_id") String pseudonym) throws JSONException, ParseException{
		String token = tokenHeader.getRequestHeader("Authorization").get(0).substring(6);
		return getMessages(token, pseudonym, 0);
	}
	
	public Response getMessages(String token, String pseudonym, int sequence_number) {			
		if(authUser(token, pseudonym)){
			StorageProviderMongoDB mongoDB = new StorageProviderMongoDB();
			List<Message> messageList = mongoDB.retrieveMessages(pseudonym, sequence_number, true);
			
			if(messageList != null){
				Gson gson = new Gson();
				
				return Response.ok(gson.toJson(messageList)).build();
			}
			
			return Response.status(204).build();
		}	
		
		return Response.status(401).build();
	}
}