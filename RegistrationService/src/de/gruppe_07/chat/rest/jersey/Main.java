package de.gruppe_07.chat.rest.jersey;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class Main {

public static void main(String[] args) throws IllegalArgumentException, IOException{
		
		final String baseUri = "http://141.19.142.61:5002/"; 
		final String paket = "de.gruppe_07.chat.rest.jersey";
		final Map<String, String> initParams = new HashMap<String, String>();
		initParams.put("com.sun.jersey.config.property.packages", paket);
		initParams.put("com.sun.jersey.spi.container.ContainerResponseFilters", "de.gruppe_07.chat.rest.jersey.CORSFilter");

		System.out.println("Starte grizzly...");
		SelectorThread threadSelector = GrizzlyWebContainerFactory.create(
				baseUri, initParams);
		System.out.printf("Grizzly l�uft unter %s%n", baseUri);
		System.out.println("[ENTER] dr�cken, um Grizzly zu beenden");
		System.in.read();
		threadSelector.stopEndpoint();
		System.out.println("Grizzly wurde beendet");
		System.exit(0);
	}

}
