package de.rest.jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HandlerHelper {
	
	public static String formatDate(Date date){
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.format(date);
	}
	
	public static Date parseDate(String date) throws ParseException{
		String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO8601);
		return sdf.parse(date);
	}
	
	@SuppressWarnings("deprecation")
	public static Date getExpireDate(){
		Date date = new Date();
		date.setHours(date.getHours() + 1);
		return date;
	}
}
