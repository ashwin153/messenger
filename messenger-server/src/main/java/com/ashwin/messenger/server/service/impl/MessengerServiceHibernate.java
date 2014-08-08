package com.ashwin.messenger.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ashwin.messenger.model.Conversation;
import com.ashwin.messenger.model.Message;
import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.server.repository.ConversationRepository;
import com.ashwin.messenger.server.repository.MessageRepository;
import com.ashwin.messenger.server.service.MessengerService;

@Service("messengerService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class MessengerServiceHibernate implements MessengerService {

	@Autowired
	private ConversationRepository _conversationRepository;
	
	@Autowired
	private MessageRepository _messageRepository;

	@Override
	public Message sendMessage(Message message) {
		// If the conversation doesn't already exist create it, otherwise update
		message.setConversation(_conversationRepository.add(message.getConversation()));
		return _messageRepository.add(message);
	}
	
//	@Override
//	public List<Conversation> getNewConversations(Student student) {
//		return _conversationRepository.getByNew(student);
//	}
	
	@Override
	public List<Message> getNewMessages(Student student) {
		return _messageRepository.getByNew(student);
	}
	
	@Override
	public List<Conversation> getConversations(String eid) {
		return _conversationRepository.getByStudentEID(eid);
	}
	
	@Override
	public List<Message> getAdditionalMessages(Student student, int id, int size, int additional) {
		// Updates the last view time for the student. This field is used by clients to determine
		// if someone has 'seen' or 'read' a message.
		Conversation conversation = _conversationRepository.getByID(id);
		conversation.getRecipient(student).setLastViewTime(System.currentTimeMillis());
		_conversationRepository.update(conversation);
		
		return _messageRepository.getAdditionalMessages(id, size, additional);
	}
	
}
