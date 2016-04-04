package org.rohit.myjaxrs.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rohit.javabrains.models.User;
import org.rohit.myjaxrs.messenger.database.MyDatabase;

public class UserService {

	private Map<String,User> users ;
	
	public UserService(){
		users = MyDatabase.getInstance().getUsers();
	}
	
	public List<User> getAllUsers(){
		return new ArrayList<User>(users.values());
	}
	
	public List<String> getAllUserNames(){
		List<String> names = new ArrayList<String>(users.keySet());
		return names;
	}

	public User getUserProfile(String aUserName) {
		
		return users.get(aUserName);
	}
}
