package de.rest.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;

@Path("register")
public class RegisterHandler {

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(String registData){
		JSONObject registDataJSON = new JSONObject(registData);
		
		if(registDataJSON.has("pseudonym") && registDataJSON.has("password") && registDataJSON.has("username")){
			Client client = Client.create();
	    	
			String success = client.resource("http://localhost:5001/mongoDB/putUser")
	        .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
	        .put(String.class, registData.toString());
	        JSONObject responseJSON = new JSONObject(success);

			client.destroy();
				        
			if(responseJSON.getBoolean("success")){
				JSONObject response = new JSONObject();
				response.put("success", true);
				
				return Response.ok(response.toString()).build();
			}
				
			return Response.status(418).build();
		}
		
		return Response.status(400).build();
	}
}
