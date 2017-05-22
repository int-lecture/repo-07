package de.rest.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;

@Path("profile")
public class ProfileHandler {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile(String profileData){
		JSONObject profileDataJSON = new JSONObject(profileData);
		
		if(profileDataJSON.has("token") && profileDataJSON.has("pseudonym")){
			Client client = Client.create();
	    	
			String profile = client.resource("http://localhost:5001/mongoDB/receiveProfile")
	        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
	        .post(String.class, profileData.toString());
			JSONObject profileJSON = new JSONObject(profile);
			
			client.destroy();
	        		
			if(profileJSON.has("pseudonym") && profileJSON.has("username") && profileJSON.has("contact"))
				return Response.ok(profile.toString()).build();
		}
		
		return Response.status(400).build();
	}
}
