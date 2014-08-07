package com.ashwin.messenger.server.service;

import java.util.List;

import com.ashwin.messenger.model.Student;

public interface DirectoryService {

	List<Student> getStudentByExample(Student example);
	Student getStudentByEID(String md5);
	Student getStudentByID(Integer studentID);
	
	Student	scrapeStudent(String eid, String password);
	Student createStudent(Student student);
	Student updateStudent(Student student);
	void removeStudent(Student student);
	
}
