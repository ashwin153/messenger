package com.ashwin.messenger.server.repository.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.server.repository.StudentRepository;
	
@Repository("studentRepository")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class StudentRepositoryHibernate implements StudentRepository {

	@Autowired
	private SessionFactory _factory;

	// Change this to getByEID (md5 hash of eid), this way you can only get the
	// conversation/messages if you know the eid hash not the id number.
	@Override
	public Student getByID(Integer studentID) {
		Student student = (Student) _factory.getCurrentSession()
				.createCriteria(Student.class)
				.add(Restrictions.idEq(studentID)).uniqueResult();
		
		if(student != null)
			Hibernate.initialize(student.getCourses());	
		
		return student;
	}
	
	@Override
	public Student getByEID(String md5) {
		Student student = (Student) _factory.getCurrentSession()
				.createCriteria(Student.class)
				.add(Restrictions.eq("EID", md5)).uniqueResult();
		
		if(student != null) {
			Hibernate.initialize(student.getCourses());
			Hibernate.initialize(student.getEID());
		}
		
		return student;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Student> getByExample(Student example) {
		List<Student> students = (List<Student>) _factory.getCurrentSession()
				.createCriteria(Student.class)
				.add(Example.create(example).enableLike(MatchMode.ANYWHERE))
				.list();
		
		if(students != null)
			for(Student student : students)
				Hibernate.initialize(student.getCourses());
		
		return students;
	}

	@Override
	public Student add(Student student) {
		_factory.getCurrentSession().saveOrUpdate(student);
		return student;
	}

	@Override
	public void remove(Student student) {
		_factory.getCurrentSession().delete(student);
	}

	@Override
	public Student update(Student student) {
		_factory.getCurrentSession().update(student);
		return student;
	}
}
