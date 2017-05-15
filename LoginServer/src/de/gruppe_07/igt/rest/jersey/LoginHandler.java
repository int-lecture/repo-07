package de.gruppe_07.igt.rest.jersey;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;



@Path("login")
public class LoginHandler extends Handler {
	// DB in mogoDb mit JSON objekten (in MongoCollection<Document> speichern)
	// token muss länger und durch zufahl generiert werden
	// token wird nach ablauf der expireDate neu generiert
	// secure random 32 bytes + timestamp + expireDate
	// bcrypt oder pbkdf2 fürs pw (eigene Klasse mit pw encode/ decode methoden)
	// passwort wird gehasht mit salt(zufallswert 32bit, getSalt()) und mit dem in datenbank abgeglichen
	// pw in db = iterations : salt : hash
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(String input) throws JSONException, UnsupportedEncodingException{
		JSONObject loginData = new JSONObject(input);
		if(isSetUserList == false){
			putUser();
			isSetUserList = true;
		}
		if(loginData.has("user") && loginData.has("password") && loginData.has("pseudonym")){
			User user;
			if((user = validateUserLogin(loginData)) != null){
				JSONObject response = new JSONObject();
				response.put("token", user.getToken());
				response.put("expire-date", formatDate(user.getExpireDate()));
				return Response.status(200).entity(response.toString()).build();
			}
			return Response.status(401).build();
		}
		return Response.status(400).build();
	}
}
