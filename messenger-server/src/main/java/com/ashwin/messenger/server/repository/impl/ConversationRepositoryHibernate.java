package com.ashwin.messenger.server.repository.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ashwin.messenger.model.Conversation;
import com.ashwin.messenger.server.repository.ConversationRepository;

@Repository("conversationRepository")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class ConversationRepositoryHibernate implements ConversationRepository {

	@Autowired
	private SessionFactory _factory;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Conversation> getByStudentEID(String eid) {
		List<Conversation> conversations = (List<Conversation>) _factory.getCurrentSession()
				.createCriteria(Conversation.class)
				.createAlias("recipients", "recipients")
				.createAlias("recipients.student", "student")
				.add(Restrictions.eq("stduent.eid", eid))
				.list();
		
		// Initializes the first message. This message is used for display purposes.
		if(conversations != null)
			for(Conversation conversation : conversations)
				if(!conversation.getMessages().isEmpty())
					Hibernate.initialize(conversation.getMessages().get(0));
		
		return conversations;
	}
	
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Conversation> getByNew(Student student) {
//		List<Conversation> conversations = (List<Conversation>) _factory.getCurrentSession()
//				.createCriteria(Conversation.class)
//				.createAlias("messages", "messages")
//				.add(Restrictions.gt("messages.sentTime", student.getLastRefreshTime()))
//				.add(Restrictions.ne("messages.sender.studentID", student.getStudentID()))
//				.list();
//		
//		if(conversations != null)
//			for(Conversation conversation : conversations)
//				if(!conversation.getMessages().isEmpty())
//					Hibernate.initialize(conversation.getMessages().get(0));
//		
//		return conversations;
//	}
	
	@Override
	public Conversation getByID(Integer conversationID) {
		return (Conversation) _factory.getCurrentSession()
				.createCriteria(Conversation.class)
				.add(Restrictions.eq("conversationID", conversationID))
				.uniqueResult();
	}
	
	@Override
	public Conversation add(Conversation conversation) {
		_factory.getCurrentSession().saveOrUpdate(conversation);
		return conversation;
	}

	@Override
	public void remove(Conversation conversation) {
		_factory.getCurrentSession().delete(conversation);
	}

	@Override
	public Conversation update(Conversation conversation) {
		_factory.getCurrentSession().update(conversation);
		return conversation;
	}
	
}
