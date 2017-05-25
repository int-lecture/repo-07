package de.gruppe_07.chat.rest.jersey;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.BsonArray;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Path("mongoDB/")
public class StorageProviderMongoDB {

	private static MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
    private static MongoClient mongoClient = new MongoClient(connectionString);
    private static MongoDatabase database = mongoClient.getDatabase("user");
	
    public synchronized String getPassword(String username, String pseudonym){
    	MongoCollection<Document> users = database.getCollection("users");
    	
    	Document foundUser = users.find(and(eq("username", username), eq("pseudonym", pseudonym))).first();
    	
    	if(foundUser != null)
    		return foundUser.getString("password");
    	
    	return null;
    }
    
    public synchronized boolean replaceTokenAndExpireDate(String username, String pseudonym, String token, Date expireDate){
    	MongoCollection<Document> users = database.getCollection("users");
    	
    	Document foundUser = users.find(and(eq("username", username), eq("pseudonym", pseudonym))).first();
    	
    	if(foundUser != null){    		
       		foundUser.replace("expireDate", HandlerHelper.formatDate(expireDate));
    		foundUser.replace("token", token);

       		users.updateOne(eq("username", username), new Document("$set", foundUser));
       	 
    		return true;
    	}
    		
    	return false;
    }
    
    public synchronized Date validToken(String token, String pseudonym) throws ParseException{
    	MongoCollection<Document> users = database.getCollection("users");
    	
    	Document foundUser = users.find(and(eq("token", token), eq("pseudonym", pseudonym))).first();
    	
    	if(foundUser != null){
    		Date expireDate = HandlerHelper.parseDate(foundUser.getString("expireDate"));
    		if(expireDate.after(new Date()))
    			return expireDate;
    	}
    	
    	return null;
    }
    
    @Path("putUser")
    @PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public synchronized Response putUser(String input) throws NoSuchAlgorithmException, InvalidKeySpecException, JSONException{
    	JSONObject userData = new JSONObject(input);
    	JSONObject response = new JSONObject();

    	String username = userData.getString("username");
    	String pseudonym = userData.getString("pseudonym");
    	
    	MongoCollection<Document> users = database.getCollection("users");
    	Document user = users.find(and(eq("username", username), eq("pseudonym", pseudonym))).first();
    	
    	if(user == null){
        	String password = SecurityHelper.hashPassword(userData.getString("password"));
        	
    		user = new Document("username",username)
    				.append("pseudonym", pseudonym).append("password", password)
    				.append("token", "").append("expireDate", "").append("contact", new BsonArray());	
    		users.insertOne(user);
    		
    		response.put("success", true);
    		return Response.ok(response.toString()).build();
    	}	
    	
    
    	response.put("success", false);
    	return Response.notModified(response.toString()).build();
    }
    
    @Path("receiveProfile")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public synchronized Response getProfile(String input){
    	JSONObject userData = new JSONObject(input);
    	String token = userData.getString("token"); 
    	String pseudonym = userData.getString("pseudonym");
    	
    	MongoCollection<Document> users = database.getCollection("users");
    	Document user = users.find(and(eq("token", token), eq("pseudonym", pseudonym))).first();

    	JSONObject response = new JSONObject();

    	if(user == null){
    		return Response.noContent().entity(response.toString()).build();
    	}
    	
    	response.put("pseudonym", pseudonym);
    	response.put("username", user.get("username"));
    	response.put("contact", user.get("contact"));
    	
    	return Response.ok(response.toString()).build();
    }
}
