package org.rohit.myjaxrs.messenger.myservice;

import java.util.Calendar;
import java.util.List;

import org.rohit.javabrains.models.Comment;
import org.rohit.myjaxrs.messenger.mydatabase.DatabaseClass;

public class CommentService {
	
	private static final DatabaseClass commentHandler = DatabaseClass.getInstance();
	
	public List<Comment> getAllComments(long messageId) {
		return null;
	}
	
	public Comment getComment(long messageId, long commentId) {
		return null;
	}
	
	public Comment addComment(long aMessageId, Comment aComment) {
		aComment.setCreated(Calendar.getInstance().getTime());
		long id = commentHandler.saveComment(aComment);
		aComment.setId(id);
		return aComment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		return null;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		return null;
	}
		
}
