package com.ashwin.messenger.server.repository;

import java.util.List;

import com.ashwin.messenger.model.Conversation;

public interface ConversationRepository extends GenericRepository<Conversation, Integer> {

	List<Conversation> getByStudentEID(String eid);
	Conversation getByID(Integer conversationID);
//	List<Conversation> getByNew(Student student);
}
