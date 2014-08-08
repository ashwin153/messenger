package com.ashwin.messenger.server.service;

import java.util.List;

import com.ashwin.messenger.model.Conversation;
import com.ashwin.messenger.model.Message;
import com.ashwin.messenger.model.Student;

public interface MessengerService {
	
	// Adds conversation if it doesn't exist
	Message sendMessage(Message message);
	List<Message> getAdditionalMessages(Student student, int conversationID, int size, int additional);
	List<Conversation> getConversations(String eid);
	
	List<Message> getNewMessages(Student student);
//	List<Conversation> getNewConversations(Student student);

}
