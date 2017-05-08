package com.example.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/messages/{user_id}/{sequence_number}")
public class MessagesHandler {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessages(@PathParam("user_id") String name, @PathParam("sequence_number") int sequence){
		User user = User.getUser(name);
		List<Message> messagesList = user.getMessages(sequence);
		if(messagesList.size() == 0)
			return Response.status(204).build();
		return Response.ok(messagesList).build();
	}
}