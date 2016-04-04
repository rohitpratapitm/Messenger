package org.rohit.myjaxrs.messenger.exception.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.rohit.javabrains.models.ErrorMessage;
import org.rohit.myjaxrs.messenger.exception.DataNotFoundException;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage message = new ErrorMessage(exception.getMessage(), "www.google.com", 404);
		return Response.status(Status.NOT_FOUND).entity(message).build();
	}

}
