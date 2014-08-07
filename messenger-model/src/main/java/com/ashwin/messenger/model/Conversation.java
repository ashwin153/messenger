package com.ashwin.messenger.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * This class represents a Conversation. Conversations contain a List of Messages
 * and a List of Recipients (Students). Whenever a message is added to a Conversation
 * it is automatically "sent" to all of the Recipients. In addition to this fields,
 * Conversations also have a name field, used to name the conversation.
 * 
 * @author Ashwin Madavan
 */
@Entity
@Table(name="CONVERSATION")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Conversation {
	
	private Integer _conversationID;
	private List<Recipient> _recipients;
	private List<Message> _messages;
	
	public Conversation() {}
	
	public Conversation(Integer conversationID, List<Recipient> recipients, List<Message> messages) {
		_conversationID = conversationID;
		_recipients = recipients;
		_messages = messages;
	}
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="ID", unique=true, nullable=false)
	public Integer getConversationID() {
		return _conversationID;
	}
	
	public void setConversationID(int conversationID) {
		_conversationID = conversationID;
	}
	
	@OneToMany(mappedBy="conversation", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<Recipient> getRecipients() {
		return _recipients;
	}
	
	public void setRecipients(List<Recipient> recipients) {
		_recipients = recipients;
	}
	
	public void addRecipient(Recipient recipient) {
		if(_recipients == null)
			_recipients = new ArrayList<Recipient>();
		
		_recipients.add(recipient);
	}
	
	// Returns the recipient for a particular student, or null if the student is not a recipient
	// to the conversation or the conversations does not have any recipients.
	@Transient
	public Recipient getRecipient(Student student) {
		if(_recipients != null)
			for(Recipient recipient : _recipients)
				if(recipient.getStudent().equals(student))
					return recipient;
		return null;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="conversation", fetch=FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OrderBy("SENT_TIME DESC")
	public List<Message> getMessages() {
		return _messages;
	}
	
	public void setMessages(List<Message> messages) {
		_messages = messages;
	}
	
	public void addMessage(Message message) {
		if(_messages == null)
			_messages = new ArrayList<Message>();
		
		_messages.add(message);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_conversationID == null) ? 0 : _conversationID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Conversation))
			return false;
		
		Conversation other = (Conversation) obj;
		if (_conversationID == null) {
			if (other._conversationID != null)
				return false;
		} else if (!_conversationID.equals(other._conversationID))
			return false;
		return true;
	}
}

//package com.ashwin.messenger.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;
//import javax.persistence.OrderBy;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//
///**
// * This class represents a Conversation. Conversations contain a List of Messages
// * and a List of Recipients (Students). Whenever a message is added to a Conversation
// * it is automatically "sent" to all of the Recipients. In addition to this fields,
// * Conversations also have a name field, used to name the conversation.
// * 
// * @author Ashwin Madavan
// */
//@Entity
//@Table(name="CONVERSATION")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
//public class Conversation {
//	
//	private Integer _conversationID;
//	private List<Student> _recipients;
//	private List<Message> _messages;
//	
//	public Conversation() {}
//	
//	public Conversation(Integer conversationID, List<Student> recipients, List<Message> messages) {
//		_conversationID = conversationID;
//		_recipients = recipients;
//		_messages = messages;
//	}
//	
//	@Id
//	@GenericGenerator(name="gen",strategy="increment")
//	@GeneratedValue(generator="gen")
//	@Column(name="ID", unique=true, nullable=false)
//	public Integer getConversationID() {
//		return _conversationID;
//	}
//	
//	public void setConversationID(int conversationID) {
//		_conversationID = conversationID;
//	}
//	
//	// Because Conversation is the owner of this relationship, you must add recipients to a
//	// conversation and NOT conversations to a student to update the database.
//	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	@JoinTable(name = "STUDENT_CONVERSATION", 
//		joinColumns = {@JoinColumn(name = "CONVERSATION_ID")}, 
//		inverseJoinColumns = {@JoinColumn(name = "STUDENT_ID")})
//	public List<Student> getRecipients() {
//		return _recipients;
//	}
//	
//	public void setRecipients(List<Student> recipients) {
//		_recipients = recipients;
//	}
//	
//	public void addRecipient(Student recipient) {
//		if(_recipients == null)
//			_recipients = new ArrayList<Student>();
//		
//		_recipients.add(recipient);
//	}
//	
//	@OneToMany(cascade=CascadeType.ALL, mappedBy="conversation", fetch=FetchType.LAZY)
//	@LazyCollection(LazyCollectionOption.EXTRA)
//	@OrderBy("SENT_TIME DESC")
//	public List<Message> getMessages() {
//		return _messages;
//	}
//	
//	public void setMessages(List<Message> messages) {
//		_messages = messages;
//	}
//	
//	public void addMessage(Message message) {
//		if(_messages == null)
//			_messages = new ArrayList<Message>();
//		
//		_messages.add(message);
//	}
//	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((_conversationID == null) ? 0 : _conversationID.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (!(obj instanceof Conversation))
//			return false;
//		
//		Conversation other = (Conversation) obj;
//		if (_conversationID == null) {
//			if (other._conversationID != null)
//				return false;
//		} else if (!_conversationID.equals(other._conversationID))
//			return false;
//		return true;
//	}
//}
