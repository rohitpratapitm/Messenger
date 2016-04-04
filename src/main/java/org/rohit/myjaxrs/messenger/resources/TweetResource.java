package org.rohit.myjaxrs.messenger.resources;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.rohit.javabrains.models.Tweet;
import org.rohit.myjaxrs.messenger.service.TweetService;

@Path("/tweets")

public class TweetResource {

	
	TweetService service = new TweetService();
	
	
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public List<Tweet> getTweets(){
		return service.getTweets();
	}
	
	@RolesAllowed("user")
	@GET
	@Path("/{tweetId}")
	@Produces (MediaType.APPLICATION_JSON)
	public Tweet getTweet(@PathParam("tweetId")long tweetId ){
		return service.getTweet(tweetId);
	}
	
	@GET
	@Produces (MediaType.APPLICATION_XML)
	public Tweet getTweetByYear(@QueryParam ("year") @DefaultValue("2013")String year)
	{
		Tweet tweetTobReturned = null;
		List<Tweet>tweets = service.getTweets();
		for(Tweet tweet:tweets){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(tweet.getCreatedAt()));
			if(calendar.get(Calendar.YEAR) == Integer.parseInt(year)){
				return tweet;
			}
		}
		return tweetTobReturned;
	}
	
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	public Response addTweet(Tweet tweet, @Context UriInfo uriInfo){
		Tweet newTweet = service.addTweet(tweet);
		String newId = String.valueOf(newTweet.getTweetId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newTweet).build();
	}
	
	@PUT
	@Path("/{tweetId}")
	@Consumes (MediaType.APPLICATION_JSON)
	public Response updateTweet(@PathParam("tweetId")long tweetId,Tweet tweet,@Context UriInfo uriInfo){
		
		Tweet oldTweet = service.getTweet(tweetId);
		if(oldTweet != null){
			oldTweet.setText(tweet.getText());
			return Response.ok().build();
		}
		return Response.notModified().build();
	}
	
	
}
