package de.gruppe_07.igt.rest.jersey;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Handler {
	
	protected static Map<String,User> userList = new HashMap<String,User>();
	protected static boolean isSetUserList = false;
	
	public void putUser() throws UnsupportedEncodingException{
		User user = new User("Dennis", "De", "YWJj", "RGVubmlz", getNewExpireDate());
		userList.put("Dennis", user);
		
		user = new User("Felix", "Fe", "ZGVm", "RmVsaXg=", getNewExpireDate());
		userList.put("Felix", user);
	}

	// Login: Wenn User registriert und das Passwort korrekt ist wird das entsprechende 
	// User Objekt zurück gegeben und das expire.date um 24h verlängert, ansonsten null
	public User validateUserLogin(JSONObject userData) throws JSONException, UnsupportedEncodingException{
		String receivedName = userData.getString("user");
		User user = userList.get(receivedName);
		if(user != null){ 
			String savedPassword = user.getPassword();
			String receivedPasswort = base64Encryption(userData.getString("password"));
			if(savedPassword.equals(receivedPasswort)){
				user.setExpireDate(getNewExpireDate());
				return user;
			}
		}
		return null;
	}
	
	// Authentifizierung: Wenn User registriert ist und das Token richtig ist wird das 
	// entsprechende User Objekt zurück gegeben und das expire-date um 24h verlängert, ansonsten null
	public User valdateUserAuth(JSONObject userData) throws JSONException{
		String pseudonym = userData.getString("pseudonym");
		String token = userData.getString("token");
		User user;
		for(Map.Entry<String, User> entry : userList.entrySet()){
			user = entry.getValue();
			if(user.getPseudonym().equals(pseudonym) && user.getToken().equals(token)){
				if(user.getExpireDate().after(new Date())){
					user.setExpireDate(getNewExpireDate());
					return user;
				}
				return null;
			}
		}
		return null;
	}
	
	// Wandelt String in base64 um
	public String base64Encryption(String user) throws UnsupportedEncodingException{
		String token = "";
		byte[] userByteArray = user.getBytes("UTF-8");
		int paddingRemain = userByteArray.length % 3;
		for(int i = 0; i < userByteArray.length; i++){
			if(i%3 == 0){
				token += byteToChar((userByteArray[i] & 0b11111100) >> 2);
			} else if(i%3 == 1){
				token += byteToChar(((userByteArray[i-1] & 0b00000011) << 4) + ((userByteArray[i] & 0b11110000) >> 4));
			} else {
				token += byteToChar(((userByteArray[i-1] & 0b00001111) << 2) + ((userByteArray[i] & 0b11000000) >> 6));
				token += byteToChar(userByteArray[i] & 0b00111111);
			}
		}
		
		if(paddingRemain == 1)
			token += (byteToChar((userByteArray[userByteArray.length-1] & 00000011) << 4)) + "==";
		else if (paddingRemain == 2)
			token += (byteToChar((userByteArray[userByteArray.length-1] & 00001111) << 2)) + "=";
		return token;
	}
	
	public char byteToChar(int b){
		if(b < 26)
			return (char) ('A' + b);
		if(b < 52)
			return (char) ('a' + b - 26);
		if(b < 62)
			return (char) ('0' + b - 52);
		if(b == 62)
			return '+';
		return '/';
	}
	
	// Date->String in ISO8601 format
	public String formatDate(Date date){
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.format(date);
	}
	
	// Gibt ein Date Objekt zurück mit dem aktuellem Datum + 24h 
	@SuppressWarnings("deprecation")
	public Date getNewExpireDate(){
		Date date = new Date();
		date.setDate(date.getDate()+1);
		return date;
	}
}
