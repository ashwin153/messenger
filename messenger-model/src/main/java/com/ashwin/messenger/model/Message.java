package com.ashwin.messenger.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="MESSAGE")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Message {
		
	private Integer _messageID;
	private Conversation _conversation;
	private Student _sender;
	private Long _sentTime;
	private String _body;
	
	public Message() {}
	
	public Message(Integer messageID, Conversation conversation, Student sender, Long sentTime, String body) {
		_messageID = messageID;
		_conversation = conversation;
		_sender = sender;
		_sentTime = sentTime;
		_body = body;
	}
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="ID", unique=true, nullable=false)
	public Integer getMessageID() {
		return _messageID;
	}
	
	public void setMessageID(int messageID) {
		_messageID = messageID;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="CONVERSATION_ID")
	public Conversation getConversation() {
		return _conversation;
	}
	
	public void setConversation(Conversation conversation) {
		_conversation = conversation;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SENDER_ID")
	public Student getSender() {
		return _sender;
	}
	
	public void setSender(Student sender) {
		_sender = sender;
	}
	
	@Column(name="BODY")
	@Type(type="text")
	public String getBody() {
		return _body;
	}
	
	public void setBody(String body) {
		_body = body;
	}
	
	@Column(name="SENT_TIME")
	public Long getSentTime() {
		return _sentTime;
	}
	
	public void setSentTime(Long sentTime) {
		_sentTime = sentTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_messageID == null) ? 0 : _messageID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Message))
			return false;
		
		Message other = (Message) obj;
		if (_messageID == null) {
			if (other._messageID != null)
				return false;
		} else if (!_messageID.equals(other._messageID))
			return false;
		return true;
	}
}
