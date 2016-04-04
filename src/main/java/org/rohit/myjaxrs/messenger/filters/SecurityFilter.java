package org.rohit.myjaxrs.messenger.filters;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter{

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic";
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		List<String>authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
		if(authHeader != null && authHeader.size() > 0){
			String authToken = authHeader.get(0);
			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
			String decodedAuthToken = Base64.decodeAsString(authToken.trim());
			StringTokenizer tokenizer = new StringTokenizer(decodedAuthToken,":");
			String username = tokenizer.nextToken();
			String password = tokenizer.nextToken();
			
			if("admin".equals(username) && "admin".equals(password)){
				return;
			}
		}
		//requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity("Username/Password didn't match").build());
	}

}
