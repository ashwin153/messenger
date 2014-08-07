package com.ashwin.messenger.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="COURSE")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Course {

	private Integer _courseID;
	private String _department, _code, _number, _name;
	private List<Timing> _timings;

	public Course() {}
	
	public Course(Integer courseID, String department, String code, 
			String name, String number, List<Timing> locations) {
		
		_courseID = courseID;
		_department = department;
		_code = code;
		_name = name;
		_number = number;
		_timings = locations;
	}
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="ID", unique=true, nullable=false)
	public Integer getCourseID() {
		return _courseID;
	}
	
	public void setCourseID(Integer courseID) {
		_courseID = courseID;
	}
	
	@Column(name="DEPARTMENT")
	public String getDepartment() {
		return _department;
	}
	
	public void setDepartment(String department) {
		_department = department;
	}
	
	@Column(name="CODE", length = 5)
	public String getCode() {
		return _code;
	}
	
	public void setCode(String code) {
		_code = code;
	}
	
	@Column(name="NAME")
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	@Column(name="NUMBER")
	public String getNumber() {
		return _number;
	}
	
	public void setNumber(String number) {
		_number = number;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "COURSE_TIMING",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TIMING_ID"))
	public List<Timing> getTimings() {
		return _timings;
	}

	public void setTimings(List<Timing> timings) {
		_timings = timings;
	}
	
	public void addTiming(Timing timing) {
		if(_timings == null)
			_timings = new ArrayList<Timing>();
		
		_timings.add(timing);
	}
}
