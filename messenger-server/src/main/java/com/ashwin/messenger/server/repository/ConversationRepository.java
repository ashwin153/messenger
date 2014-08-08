package com.ashwin.messenger.server.repository;

import java.util.List;

import com.ashwin.messenger.model.Conversation;
import com.ashwin.messenger.model.Student;

public interface ConversationRepository extends GenericRepository<Conversation, Integer> {

	List<Conversation> getByStudentEID(String eid);
	Conversation getByID(Integer conversationID);
//	List<Conversation> getByNew(Student student);
}
