package org.rohit.myjaxrs.messenger.mydatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.rohit.javabrains.models.Comment;
import org.rohit.javabrains.models.Message;
import org.rohit.javabrains.models.Profile;

public class DatabaseClass {
	
	public static Logger log = Logger.getLogger(DatabaseClass.class);
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();
	private static DatabaseClass instance;
	private final SessionFactory sessionFactory;
	
	@SuppressWarnings("deprecation")
	private DatabaseClass(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	public static DatabaseClass getInstance(){
		if(instance == null){
			instance = new DatabaseClass();
		}
		return instance;
	}
	
	public List<Message> getMessages() {
		
		Session session = sessionFactory.openSession();
		List<Message> messages = null;
		try{
			session.beginTransaction();
			Query query = session.createQuery("from Message");
			messages = (List<Message>)query.list();
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return messages;
	}
	
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
	public static DatabaseClass getProfileHandler() {
		
		return null;
	}
	
	public long saveProfile(Profile aProfile){
		
		long id = -1;
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			id = (long)session.save(aProfile);
			session.getTransaction().commit();
		}catch(Exception e){
			log.error("Error while saving profile object"+"\n"+ e.getMessage());
			throw e;
		}finally{
			session.close();
		}
		return id;
	}

	public Profile getProfile(String profileName) {

		Session session = sessionFactory.openSession();
		Profile profile = null;
		try{
			session.beginTransaction();
			profile = (Profile)session.get(Profile.class, profileName);
			session.getTransaction().commit();
		}catch(Exception e){
			log.error("Error while fetching profile"+"\n"+e.getMessage());
			throw e;
		}finally{
			session.close();
		}
		return profile;
	}

	public void updateProfile(Profile aProfile) {
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(aProfile);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			log.error("Error while fetching profile"+"\n"+e.getMessage());
			throw e;
		}finally{
			session.close();
		}
	}

	public void deleteProfile(String profileName) {

		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Profile profile = (Profile)session.get(Profile.class, profileName);
			if(profile != null){
				session.delete(profile);
				session.getTransaction().commit();
			}
		}catch(Exception e){
			session.getTransaction().rollback();
			log.error("Error while fetching profile"+"\n"+e.getMessage());
			throw e;
		}finally{
			session.close();
		}
	}

	public Message getMessage(long aMessageId) {
		
		Session session = sessionFactory.openSession();
		Message message = null;
		try{
			session.beginTransaction();
			message = (Message)session.get(Message.class, aMessageId);
			session.getTransaction().commit();
		}catch(Exception e){
			log.error("Error while fetching profile"+"\n"+e.getMessage());
			throw e;
		}finally{
			session.close();
		}
		return message;
	}
	
	public long saveMessage(Message aMessage){
		
		long id = -1;
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			id = (long)session.save(aMessage);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			log.error("Error while saving profile object"+"\n"+ e.getMessage());
			throw e;
		}finally{
			session.close();
		}
		return id;
	}

	public long saveComment(Comment aComment) {
		
		long id = -1;
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			id = (long)session.save(aComment);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			log.error("Error while saving profile object"+"\n"+ e.getMessage());
			throw e;
		}finally{
			session.close();
		}
		return id;
	}

	public int deleteMessage(long id) {
		
		int rowsEffected = -1;
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Query deleteQuery = session.createQuery("from Message where id="+id);
			rowsEffected = deleteQuery.executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		
		return rowsEffected;
	}
}
