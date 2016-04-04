package org.rohit.myjaxrs.messenger.exception;

public class DataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817765105154513439L;
	
	public DataNotFoundException(String message){
		super(message);
		
	}

}
