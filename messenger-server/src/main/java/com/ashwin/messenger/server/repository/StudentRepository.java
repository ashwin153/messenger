package com.ashwin.messenger.server.repository;

import java.util.List;

import com.ashwin.messenger.model.Student;

public interface StudentRepository extends GenericRepository<Student, Integer> {

	Student getByID(Integer studentID);
	
	// Select a student repository by the EID, this is used for security purposes
	Student getByEID(String md5);
	
	// No new methods to add at this point in time.
	List<Student> getByExample(Student example);
	
}
