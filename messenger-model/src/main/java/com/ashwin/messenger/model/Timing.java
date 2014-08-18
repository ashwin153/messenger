package com.ashwin.messenger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * This class represents the time/location that a Course takes place. Every Course has multiple
 * Timings throughout the week (on different days a Course might take place at a different timing).
 *
 * @author Ashwin Madavan
 */
@Entity
@Table(name="TIMING")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Timing {
	
	private Integer _courseLocationID;
	private String _building;
	private String _room;
	private Day _day;
	private Long _startTime, _endTime;
	
	public enum Day {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;
	}
	
	public Timing() {}
	
	public Timing(Integer courseLocationID, String building, String room, Day day, Long startTime, Long endTime) {
		_courseLocationID = courseLocationID;
		_building = building;
		_room = room;
		_day = day;
		_startTime = startTime;
		_endTime = endTime;
	}
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="ID", unique=true, nullable=false)
	public Integer getCourseLocationID() {
		return _courseLocationID;
	}
	
	public void setCourseLocationID(int courseLocationID) {
		_courseLocationID = courseLocationID;
	}
	
	@Column(name="BUILDING")
	public String getBuilding() {
		return _building;
	}
	
	public void setBuilding(String building) {
		_building = building;
	}
	
	@Column(name="ROOM")
	public String getRoom() {
		return _room;
	}
	
	public void setRoom(String room) {
		_room = room;
	}
	
	@Column(name="DAY")
	@Enumerated(EnumType.STRING)
	public Day getDay() {
	    return _day;
	}
	
	public void setDay(Day day) {
		_day = day;
	}
	
	@Column(name="START_TIME")
	public Long getStartTime() {
		return _startTime;
	}
	
	public void setStartTime(Long startTime) {
		_startTime = startTime;
	}
	
	@Column(name="END_TIME")
	public Long getEndTime() {
		return _endTime;
	}
	
	public void setEndTime(Long endTime) {
		_endTime = endTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_courseLocationID == null) ? 0 : _courseLocationID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Timing))
			return false;
		
		Timing other = (Timing) obj;
		if (_courseLocationID == null) {
			if (other._courseLocationID != null)
				return false;
		} else if (!_courseLocationID.equals(other._courseLocationID))
			return false;
		return true;
	}
}
