package de.gruppe_07.rest.jersey;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


@Path("/messages/{user_id}/{sequence_number}")
public class MessagesHandler {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessages(@PathParam("user_id") String name, @PathParam("sequence_number") int sequence){
		User user = User.getUser(name);
		if(user != null){
			List<Message> messagesList = user.getMessages(sequence);
			if(messagesList.size() == 0)
				return Response.status(204).build();
			Gson gson = new Gson();
			return Response.ok(gson.toJson(messagesList)).build();
		}
		return Response.status(204).build();
	}
}