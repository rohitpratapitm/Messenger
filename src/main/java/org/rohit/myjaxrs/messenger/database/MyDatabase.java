package org.rohit.myjaxrs.messenger.database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.rohit.javabrains.models.Tweet;
import org.rohit.javabrains.models.TwitterRecord;
import org.rohit.javabrains.models.User;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyDatabase {

	private static MyDatabase instance;
	private static List<TwitterRecord> records;
	private static Map<Long,Tweet> tweets;
	private final String location = "http://www.cs.toronto.edu/~mashiyat/csc309/Assignments/favs.json";
	private final SessionFactory sessionFactory;
	
	private MyDatabase(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
		initialize();
	}
	
	private void initialize() {
		
		ObjectMapper mapper = new ObjectMapper();///messenger/src/main/resources/Twitter.json
		boolean fallback = false;
		try {
			records = mapper.readValue(new URL(location), new TypeReference<List<TwitterRecord>>() {});
		}catch (IOException e) {
			System.out.println("ERROR: URL Location not accessible"+e.getLocalizedMessage());
			fallback = true;
		}
		if(fallback){
			try{
				mapper.configure(Feature.ALLOW_COMMENTS, true);
				mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
				records = mapper.readValue(new File("D:\\J2EE\\messenger\\src\\Twitter.json"), new TypeReference<List<TwitterRecord>>() {});
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		initializeTweets();
	}
	public static void main(String args[]){
		MyDatabase database = MyDatabase.getInstance();
		database.getRecords();
	}
	public static MyDatabase getInstance(){
		if(instance == null){
			instance = new MyDatabase();
		}
		return instance;
	}
	
	public List<TwitterRecord> getRecords(){
		return records;
	}
	
	public Map<String,User> getUsers(){
		
		Map<String,User> users = new HashMap<>();
		for(TwitterRecord record: records){
			User user = record.getUser();
			if(user != null){
				saveUser(user);
				users.put(user.getScreenName(),user);
			}
		}
		return users;
	}
	
	class IdBasedComparator implements Comparator<Long>{

		@Override
		public int compare(Long id1, Long id2) {
			return id1.compareTo(id2);
		}
		
	}
	public Map<Long,Tweet> getTweets(){
		
		Session session = sessionFactory.openSession();
		return tweets;
	}
	
	public void initializeTweets(){
		tweets = new TreeMap<>(new IdBasedComparator());
		for(TwitterRecord record: records){
			if(record != null){
				Tweet tweet = new Tweet();
				tweet.setTweetId(record.getId());
				tweet.setCreatedAt(record.getCreatedAt());
				tweet.setText(record.getText());
				tweet.setUserName(record.getUser().getName());
				saveTweet(tweet);
				tweets.put(record.getId(),tweet);
			}
		}
	}
	
	public void saveUser(User aUser){
		
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.save(aUser);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
	}

	public void saveTweet(Tweet aTweet){
		
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.save(aTweet);
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
	}
}
