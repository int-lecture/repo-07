package de.gruppe_07.igt.rest.jersey;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;


@Path("auth")
public class AuthentificationHandler extends Handler{

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response authentification(String input) throws JSONException, UnsupportedEncodingException{
		JSONObject authData = new JSONObject(input);
		if(isSetUserList == false){
			putUser();
			isSetUserList = true;
		}
		User user = valdateUserAuth(authData);
		if(user != null){
			JSONObject response = new JSONObject();
			response.put("success", true);
			response.put("expire-date", formatDate(user.getExpireDate()));
			return Response.status(200).entity(response.toString()).build();
		}
		return Response.status(401).build();
	}
}
