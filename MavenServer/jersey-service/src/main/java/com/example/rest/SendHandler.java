package com.example.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("send")
public class SendHandler{
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putMessage(Message message){
		if(message.complete()){
			User user = User.getUser(message.getTo());
			if(user == null)
				user = new User(message.getTo());
			user.saveMessage(message);
			return Response.status(201).entity(new Response201(user.getSequence()-1)).build();
		}
		return Response.status(400).build();
	}
	
}
