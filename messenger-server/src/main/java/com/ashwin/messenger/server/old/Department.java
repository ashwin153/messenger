package com.ashwin.messenger.server.old;
//package com.ashwin.messenger.old;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Type;
//
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//
//@Entity
//@Table(name="DEPARTMENT")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
//public class Department {
//
//	private Integer _departmentID;
//	private String _code, _name;
//	
//	public Department(Integer departmentID, String name, String code) {
//		_departmentID = departmentID;
//		_name = name;
//		_code = code;
//	}
//	
//	@Id
//	@GenericGenerator(name="gen",strategy="increment")
//	@GeneratedValue(generator="gen")
//	@Column(name="ID", unique=true, nullable=false)
//	public Integer getDepartmentID() {
//		return _departmentID;
//	}
//	
//	public void setDepartmentID(Integer departmentID) {
//		_departmentID = departmentID;
//	}
//	
//	@Column(name="CODE", length = 3)
//	public String getCode() {
//		return _code;
//	}
//	
//	public void setCode(String code) {
//		_code = code;
//	}
//	
//	@Column(name="NAME")
//	public String getName() {
//		return _name;
//	}
//	
//	public void setName(String name) {
//		_name = name;
//	}
//	
//	@Override
//	public String toString() {
//		return "Department [departmentID="+_departmentID+", name="+_name+", code="+_code+"]";
//	}
//}
