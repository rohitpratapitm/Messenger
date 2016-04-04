package org.rohit.myjaxrs.messenger.myservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.rohit.javabrains.models.Message;
import org.rohit.myjaxrs.messenger.exception.DataNotFoundException;
import org.rohit.myjaxrs.messenger.mydatabase.DatabaseClass;

public class MessageService {

	private static final DatabaseClass messageHandler = DatabaseClass.getInstance();
	
	public MessageService() {
		
	}
	
	public List<Message> getAllMessages() {
		return messageHandler.getMessages(); 
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		
		return messagesForYear;
	}
	/*
	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if (start + size > list.size()) return new ArrayList<Message>();
		return list.subList(start, start + size); 
	}*/
	
	
	public Message getMessage(long id)throws DataNotFoundException {
		Message message = messageHandler.getMessage(id);
		if(message == null){
			throw new DataNotFoundException("No message found with id "+id);
		}
		return message; 
	}
	
	public Message addMessage(Message message) {
		message.setCreated(Calendar.getInstance().getTime());
		long id = messageHandler.saveMessage(message);
		message.setId(id);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		return message;
	}
	
	public boolean removeMessage(long id) {
		int noOfMessagesDeleted = messageHandler.deleteMessage(id);
		return noOfMessagesDeleted>0;
	}
	
}
