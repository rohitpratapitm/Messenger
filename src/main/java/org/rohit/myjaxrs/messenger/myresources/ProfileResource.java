package org.rohit.myjaxrs.messenger.myresources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.rohit.javabrains.models.Profile;
import org.rohit.myjaxrs.messenger.myservice.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON})
public class ProfileResource {

	private ProfileService profileService = new ProfileService();
	
	@GET
	public List<Profile> getProfiles() {
		 
		return profileService.getAllProfiles();
	}
	
	@POST
	public Response addProfile(Profile aProfile, @Context UriInfo aUriInfo) {
		Profile profile = profileService.addProfile(aProfile);
		URI uri = aUriInfo.getAbsolutePathBuilder().path(profile.getProfileName()).build();
		return Response.created(uri).entity(profile).build();
	}
	
	@GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName) {
		return profileService.getProfile(profileName);
	}
	
	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String aProfileName, Profile aProfile) {
		aProfile.setProfileName(aProfileName);
		Profile profile = profileService.updateProfile(aProfile);
		return profile;
		//return Response.ok().entity(profile).build();
	}
	
	@DELETE
	@Path("/{profileName}")
	public Response deleteProfile(@PathParam("profileName") String profileName) {
		profileService.removeProfile(profileName);
		return Response.accepted().build();
	}
}
