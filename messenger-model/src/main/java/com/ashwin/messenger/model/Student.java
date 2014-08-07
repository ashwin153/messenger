package com.ashwin.messenger.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="STUDENT")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Student {
	
	private Integer _studentID, _year;
	private String _eid, _name, _email, _phone, _major, _college, _dorm;
	private Long _lastUpdateTime, _lastRefreshTime, _birthday;
	private List<Course> _courses;
	
	public Student() {}
	
	public Student(Integer studentID, String eid, String name, String email, String phone, 
			Long birthday, Integer year, String major, String college, 
			String dorm, List<Course> courses, Long lastUpdateTime, 
			Long lastRefreshTime) {
		
		_studentID = studentID;
		_eid = eid;
		_name = name;
		_email = email;
		_phone = phone;
		_birthday = birthday;
		_year = year;
		_major = major;
		_college = college;
		_dorm = dorm;
		_courses = courses;
		_lastUpdateTime = lastUpdateTime;
		_lastRefreshTime = lastRefreshTime;
	}
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="ID", unique=true, nullable=false)
	public Integer getStudentID() {
		return _studentID;
	}
	
	public void setStudentID(Integer studentID) {
		_studentID = studentID;
	}
	
	@Column(name="EID")
	@Basic(fetch=FetchType.LAZY)
	public String getEID() {
		return _eid;
	}
	
	public void setEID(String eid) {
		_eid = eid;
	}
	
	@Column(name="NAME")
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	@Column(name="EMAIL")
	public String getEmail() {
		return _email;
	}
	
	public void setEmail(String email) {
		_email = email;
	}
	
	@Column(name="PHONE")
	public String getPhone() {
		return _phone;
	}
	
	public void setPhone(String phone) {
		_phone = phone;
	}
	
	@Column(name="BIRTHDAY")
	public Long getBirthday() {
		return _birthday;
	}
	
	public void setBirthday(Long birthday) {
		_birthday = birthday;
	}
	
	@Column(name="YEAR")
	public Integer getYear() {
		return _year;
	}
	
	public void setYear(Integer year) {
		_year = year;
	}
	
	@Column(name="MAJOR")
	public String getMajor() {
		return _major;
	}
	
	public void setMajor(String major) {
		_major = major;
	}
	
	@Column(name="COLLEGE")
	public String getCollege() {
		return _college;
	}
	
	public void setCollege(String college) {
		_college = college;
	}
	
	@Column(name="DORM")
	public String getDorm() {
		return _dorm;
	}
	
	public void setDorm(String dorm) {
		_dorm = dorm;
	}
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinTable(name="STUDENT_COURSE", 
		joinColumns = { @JoinColumn(name="STUDENT_ID") }, 
		inverseJoinColumns = { @JoinColumn(name="COURSE_ID")})
	public List<Course> getCourses() {
		return _courses;
	}
	
	public void setCourses(List<Course> courses) {
		_courses = courses;
	}
	
	public void addCourse(Course course) {
		if(_courses == null)
			_courses = new ArrayList<Course>();
		
		_courses.add(course);
	}
	
	@Column(name="LAST_UPDATE_TIME")
	public Long getLastUpdateTime() {
		return _lastUpdateTime;
	}
	
	public void setLastUpdateTime(Long lastUpdateTime) {
		_lastUpdateTime = lastUpdateTime;
	}
	
	@Column(name="LAST_REFRESH_TIME")
	public Long getLastRefreshTime() {
		return _lastRefreshTime;
	}
	
	public void setLastRefreshTime(Long lastRefreshTime) {
		_lastRefreshTime = lastRefreshTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_studentID == null) ? 0 : _studentID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Student))
			return false;
		
		Student other = (Student) obj;
		if (_studentID == null) {
			if (other._studentID != null)
				return false;
		} else if (!_studentID.equals(other._studentID))
			return false;
		return true;
	}
}
