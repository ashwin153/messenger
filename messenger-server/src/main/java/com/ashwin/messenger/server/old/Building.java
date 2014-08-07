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
//@Table(name="BUILDING")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
//public class Building {
//
//	private Integer _buildingID;
//	private String _name, _code, _address;
//	
//	public Building(Integer buildingID, String name, String code, String address) {
//		_buildingID = buildingID;
//		_name = name;
//		_code = code;
//		_address = address;
//	}
//	
//	@Id
//	@GenericGenerator(name="gen",strategy="increment")
//	@GeneratedValue(generator="gen")
//	@Column(name="ID", unique=true, nullable=false)
//	public Integer getBuildingID() {
//		return _buildingID;
//	}
//	
//	public void setBuildingID(Integer buildingID) {
//		_buildingID = buildingID;
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
//	@Column(name="ADDRESS")
//	public String getAddress() {
//		return _address;
//	}
//	
//	public void setAddress(String address) {
//		_address = address;
//	}
//	
//	@Override
//	public String toString() {
//		return "Building [buildingID="+_buildingID+", name="+_name+
//				", code="+_code+", address="+_address+"]";
//	}
//}
