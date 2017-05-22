package de.rest.jersey;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;


@Path("auth")
public class AuthentificationHandler{

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response authentification(String input) throws JSONException, UnsupportedEncodingException, ParseException{
		JSONObject authData = new JSONObject(input);
		
		if(authData.has("pseudonym") && authData.has("token")){
			StorageProviderMongoDB mongoDB = new StorageProviderMongoDB(); 
			String pseudonym = authData.getString("pseudonym");
			String token = authData.getString("token");
			Date expireDate = mongoDB.validToken(token, pseudonym);
			
			if(expireDate != null){
				JSONObject response = new JSONObject();
				response.put("success", true);
				response.put("expire-date", HandlerHelper.formatDate(expireDate));
				
				return Response.status(200).entity(response.toString()).build();
			}
		}
		
		return Response.status(401).build();
	}
}
