package de.rest.jersey;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("login")
public class LoginHandler{
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(String input) throws UnsupportedEncodingException, JSONException, NoSuchAlgorithmException, InvalidKeySpecException{
		JSONObject loginData = new JSONObject(input);
		
		if(loginData.has("username") && loginData.has("password") && loginData.has("pseudonym")){
			String username = loginData.getString("username");
			String pseudonym = loginData.getString("pseudonym");
			String password = loginData.getString("password");
			
			StorageProviderMongoDB mongoDB = new StorageProviderMongoDB();
			String storedPassword = mongoDB.getPassword(username, pseudonym);
			
			if((SecurityHelper.validatePassword(password, storedPassword))){
				Date expireDate = HandlerHelper.getExpireDate();
				String token = SecurityHelper.encodeToken(HandlerHelper.formatDate(expireDate));
				boolean success = mongoDB.replaceTokenAndExpireDate(username, pseudonym, token, expireDate);
				
				if(success){
					JSONObject response = new JSONObject();
					response.put("token", token);
					response.put("expire-date", HandlerHelper.formatDate(expireDate));
					
					return Response.status(200).entity(response.toString()).build();
				}
			}
			
			return Response.status(401).build();
		}
		
		return Response.status(400).build();
	}	
}
