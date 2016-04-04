package org.rohit.myjaxrs.messenger.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("webapi")
public class MessengerApplication extends ResourceConfig {
	
	public MessengerApplication(){
		
		packages("org.rohit.myjaxrs.messenger");
		register(RolesAllowedDynamicFeature.class);
	}

}
