package com.ashwin.messenger.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.scraper.ScraperUtils;
import com.ashwin.messenger.scraper.StudentScraper;
import com.ashwin.messenger.server.service.DirectoryService;

@Controller
@RequestMapping("/directory")
public class DirectoryController {

	@Autowired
	private DirectoryService _directoryService;
	
	// Scrapes a students profile off of UT servers using their actual eid and password
	@RequestMapping(method=RequestMethod.POST, value="/{eid}/search")
	public @ResponseBody List<Student> searchStudent(@PathVariable String eid, @RequestBody Student example) {
		if(_directoryService.getStudentByEID(eid) == null)
			return null;
		
		return _directoryService.getStudentByExample(example);
	}
	
	// Gets a student by their unique EID hash. This hash serves as an autherization
	// token to access other parts of UT Messenger.
	@RequestMapping(method=RequestMethod.GET, value="/{eid}")  
	public @ResponseBody Student getStudent(@PathVariable String eid) { 
	    return _directoryService.getStudentByEID(eid);
	}
	
	// Gets the details of a student by their id number. This method does not initialize
	// the Conversations list, so students chat history is protected.
	@RequestMapping(method=RequestMethod.GET, value="/{eid}/{id}")  
	public @ResponseBody Student getStudentDetails(@PathVariable String eid, @PathVariable String id) { 
		if(_directoryService.getStudentByEID(eid) == null)
			return null;
		
		return _directoryService.getStudentByID(Integer.valueOf(id));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/scrape")
	public @ResponseBody Student scrapeStudent(@RequestParam("eid") String eid, @RequestParam("password") String password) {
		String md5 = ScraperUtils.getMD5(eid);
		Student student = _directoryService.getStudentByEID(md5);
		if(student != null)
			return student;
		
		return StudentScraper.scrape(eid, password);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/create")
	public @ResponseBody Student scrapeStudent(@RequestBody Student student) {
		return _directoryService.createStudent(student);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{eid}/update")
	public Student updateStudent(@PathVariable String eid, @RequestBody Student student) {
		if(_directoryService.getStudentByEID(eid) != null)
			return null;
		
		return _directoryService.updateStudent(student);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{eid}/remove")
	public void deleteStudent(@PathVariable String eid, @RequestBody Student student) {
		if(_directoryService.getStudentByEID(eid) != null)
			_directoryService.removeStudent(student);
	}
}
