package org.rohit.myjaxrs.messenger.myservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.rohit.javabrains.models.Profile;
import org.rohit.myjaxrs.messenger.mydatabase.DatabaseClass;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	private final DatabaseClass profileHandler = DatabaseClass.getInstance();
	
	public ProfileService() {
		profiles.put("rohit", new Profile(1, "rohit", "Rohit", "Singh"));
	}
	
	public List<Profile> getAllProfiles() {
		//TODO use hibernate to fetch records
		return new ArrayList<Profile>(profiles.values()); 
	}
	
	public Profile getProfile(String aProfileName) {
		
		if(aProfileName != null && !aProfileName.isEmpty()){
			return profileHandler.getProfile(aProfileName);
		}
		return null;
	}
	
	public Profile addProfile(Profile profile) {
		profile.setCreated(Calendar.getInstance().getTime());
		long id = profileHandler.saveProfile(profile);
		profile.setId(id);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profileHandler.updateProfile(profile);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public void removeProfile(String profileName) {
		 profileHandler.deleteProfile(profileName);
	}
	
	
}
