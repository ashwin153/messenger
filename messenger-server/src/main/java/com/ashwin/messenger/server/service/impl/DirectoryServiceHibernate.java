package com.ashwin.messenger.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.scraper.StudentScraper;
import com.ashwin.messenger.server.repository.StudentRepository;
import com.ashwin.messenger.server.service.DirectoryService;

@Service("directoryService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class DirectoryServiceHibernate implements DirectoryService {

	@Autowired
	private StudentRepository _studentRepository;
	
	@Override
	public List<Student> getStudentByExample(Student example) {
		return _studentRepository.getByExample(example);
	}

	@Override
	public Student getStudentByEID(String md5) {
		return _studentRepository.getByEID(md5);
	}

	@Override
	public Student getStudentByID(Integer studentID) {
		return _studentRepository.getByID(studentID);
	}
	
	@Override
	public Student scrapeStudent(String eid, String password) {
		return StudentScraper.scrape(eid, password);
	}

	@Override
	public Student createStudent(Student student) {
		return _studentRepository.add(student);
	}

	@Override
	public Student updateStudent(Student student) {
		return _studentRepository.update(student);
	}

	@Override
	public void removeStudent(Student student) {
		_studentRepository.remove(student);
	}
}
