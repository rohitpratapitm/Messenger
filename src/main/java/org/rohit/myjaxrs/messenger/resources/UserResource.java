package org.rohit.myjaxrs.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.rohit.javabrains.models.User;
import org.rohit.myjaxrs.messenger.service.UserService;

@Path("/users")
public class UserResource {

	private UserService service = new UserService();
	
	
	@GET
	@Produces(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
	public List<User> getUsers(){
		return service.getAllUsers();
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username")String userName){
		
		return service.getUserProfile(userName);	
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User aUser, @Context UriInfo aUriInfo){
		return Response.notModified().encoding("Not Supported").build();
	}
}
