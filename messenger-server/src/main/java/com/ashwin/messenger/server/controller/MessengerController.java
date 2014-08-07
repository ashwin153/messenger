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

import com.ashwin.messenger.model.Conversation;
import com.ashwin.messenger.model.Message;
import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.server.service.DirectoryService;
import com.ashwin.messenger.server.service.MessengerService;

@Controller
@RequestMapping("/messenger")
public class MessengerController {
	
	@Autowired
	private MessengerService _messengerService;
	
	@Autowired
	private DirectoryService _directoryService;

	@RequestMapping(method=RequestMethod.GET, value="{eid}/conversation/{id}")
	public @ResponseBody List<Message> getAdditionalMessages(@PathVariable String eid, 
			@PathVariable int id, @RequestParam("size") int size, @RequestParam("additional") int additional) {
		Student student = _directoryService.getStudentByEID(eid);
		if(student == null) return null;
		
		return _messengerService.getAdditionalMessages(student, id, size, additional);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="{eid}/conversation")
	public @ResponseBody List<Conversation> getConversations(@PathVariable String eid) {
		if(_directoryService.getStudentByEID(eid) == null) return null;
		
		return _messengerService.getConversations(eid);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="{eid}/update")
	public @ResponseBody List<Message> getNewMessages(@PathVariable String eid) {
		Student student = _directoryService.getStudentByEID(eid);
		if(student == null) return null;
		
		List<Message> messages = _messengerService.getNewMessages(student);
		student.setLastRefreshTime(System.currentTimeMillis());
		_directoryService.updateStudent(student);
		
		return messages;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="{eid}/send")
	public @ResponseBody Message sendMessage(@PathVariable String eid, @RequestBody Message message) {
		if(_directoryService.getStudentByEID(eid) == null) return null;
		return _messengerService.sendMessage(message);
	}
	
}
