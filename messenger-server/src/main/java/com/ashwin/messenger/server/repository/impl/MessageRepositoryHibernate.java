package com.ashwin.messenger.server.repository.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ashwin.messenger.model.Message;
import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.server.repository.MessageRepository;

@Repository("messageRepository")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class MessageRepositoryHibernate implements MessageRepository {
	
	@Autowired
	private SessionFactory _factory;

	@Override
	@SuppressWarnings("unchecked")
	public List<Message> getByNew(Student student) {
		return (List<Message>) _factory.getCurrentSession()
				.createCriteria(Message.class)
				.createAlias("sender", "sender")
				.add(Restrictions.gt("SENT_TIME", student.getLastRefreshTime()))
				.add(Restrictions.ne("sender.studentID", student.getStudentID()))
				.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Message> getAdditionalMessages(int conversationID, int size, int additional) {
		return (List<Message>) _factory.getCurrentSession()
				.createCriteria(Message.class)
				.setFirstResult(size)
				.setMaxResults(additional)
				.createAlias("conversation", "conversation")
				.add(Restrictions.eq("conversation.conversationID", conversationID))
				.list();
	}

	@Override
	public Message add(Message message) {
		_factory.getCurrentSession().saveOrUpdate(message);
		return message;
	}

	@Override
	public void remove(Message message) {
		_factory.getCurrentSession().delete(message);
	}

	@Override
	public Message update(Message message) {
		_factory.getCurrentSession().update(message);
		return message;
	}
	
}
