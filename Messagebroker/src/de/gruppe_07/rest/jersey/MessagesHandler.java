package de.gruppe_07.rest.jersey;

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

import org.codehaus.jettison.json.JSONException;

import com.google.gson.Gson;

// /messages/userid/ testen
@Path("/messages/{user_id}/{sequence_number}")
public class MessagesHandler {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessages(@Context HttpHeaders tokenHeader, @PathParam("user_id") String name, @PathParam("sequence_number") int sequence) throws JSONException, ParseException{
		User user = User.getUser(name);
		if(user != null){
			String token = tokenHeader.getRequestHeader("Authorization").get(0).substring(6);
			if(user.validUser(token, name)){
				List<Message> messageList = user.getMessages(sequence);
				if(messageList.size() != 0){
					Gson gson = new Gson();
					return Response.ok(gson.toJson(messageList).toString()).build();
				}
				return Response.status(204).build();
			}	
			return Response.status(401).build();
		}
		return Response.status(204).build();
	}
}