package com.ashwin.messenger.server.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.server.config.MessengerConfig;
import com.ashwin.messenger.server.repository.StudentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MessengerConfig.class})
@TransactionConfiguration(defaultRollback=true)
@WebAppConfiguration
public class StudentRepositoryTest extends TestCase {

	@Autowired
	private StudentRepository _studentRepository;
	
	@Test
	public void testStudentRepository() {
		// Initialize a student with test data.
		Student student = getDummyStudent();

		// Add the student to the database
		_studentRepository.add(student);
		
		// Alter the student in the database
		student.setEmail("changed@test.com");
		_studentRepository.update(student);
		
		// Load the student by it's student ID, and ensure that the student
		// was successfully added and altered.
		Student id = _studentRepository.getByID(student.getStudentID());
		assertEquals(student.getStudentID(), id.getStudentID());
		assertEquals(id.getEmail(), "changed@test.com");
		
		// Test each of the select statements for the repository
		Student eid = _studentRepository.getByEID(student.getEID());
		List<Student> example = _studentRepository.getByExample(student);
		assertEquals(id, eid);
		assertNotNull(example);
		assertNotSame(example.size(), 0);
		assertEquals(id, example.get(0));
	}
	
	public Student getDummyStudent() {
		return new Student(null, "abcdefghijklmnopqrstuvwxyz", "Test McTesties", "test@test.com", 
				"1231234567", 827971200000L, 2018, "B.S. Computer Science", "College of Natural Sciences",
				"W0312", null, System.currentTimeMillis(), System.currentTimeMillis());
	}
}
