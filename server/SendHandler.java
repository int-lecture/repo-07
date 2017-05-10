package de.gruppe_07.rest.jersey;

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

@Path("send")
public class SendHandler{
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putMessage(JSONObject messageJson) throws JSONException{
		Message message = new Message(messageJson.getString("from"), messageJson.getString("to"), messageJson.getString("date"), messageJson.getString("text"));
		if(message.complete()){
			User user = User.getUser(message.getTo());
			if(user == null)
				user = new User(message.getTo());
			user.saveMessage(message);
			JSONObject response = new JSONObject();
			String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";
			SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
			response.put("date", sdf.format(new Date()));
			response.put("sequence", user.getSequence()-1);
			return Response.status(201).entity(response).build();
		}
		return Response.status(400).build();
	}
	
}
