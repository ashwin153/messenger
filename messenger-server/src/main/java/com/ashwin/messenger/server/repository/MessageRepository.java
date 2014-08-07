package com.ashwin.messenger.server.repository;

import java.util.List;

import com.ashwin.messenger.model.Conversation;
import com.ashwin.messenger.model.Message;
import com.ashwin.messenger.model.Student;

public interface MessageRepository extends GenericRepository<Message, Integer> {

	List<Message> getByNew(Student student);
	List<Message> getAdditionalMessages(int cosversationID, int size, int additional);
	
}
