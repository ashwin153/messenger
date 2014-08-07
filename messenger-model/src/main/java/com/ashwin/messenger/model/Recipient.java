package com.ashwin.messenger.model;

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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="RECIPIENT")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Recipient {
	
	private Integer _recipientID;
	private Student _student;
	private Conversation _conversation;
	private long _lastViewTime;
	
	public Recipient(Integer recipientID, Student student, Conversation conversation, Long lastViewTime) {
		_recipientID = recipientID;
		_student = student;
		_conversation = conversation;
		_lastViewTime = lastViewTime;
	}
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="ID", unique=true, nullable=false)
	public Integer getRecipientID() {
		return _recipientID;
	}
	
	public void setRecipientID(Integer recipientID) {
		_recipientID = recipientID;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="STUDENT_ID")
	public Student getStudent() {
		return _student;
	}
	
	public void setStudent(Student student) {
		_student = student;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CONVERSATION_ID")
	public Conversation getConversation() {
		return _conversation;
	}
	
	public void setConversation(Conversation conversation) {
		_conversation = conversation;
	}
	
	@Column(name="LAST_VIEW_TIME")
	public Long getLastViewTime() {
		return _lastViewTime;
	}
	
	public void setLastViewTime(Long lastViewTime) {
		_lastViewTime = lastViewTime;
	}
}
