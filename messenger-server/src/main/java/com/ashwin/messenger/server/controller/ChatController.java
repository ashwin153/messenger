package com.ashwin.messenger.server.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.ashwin.messenger.model.Message;
import com.ashwin.messenger.model.Student;
import com.ashwin.messenger.server.service.DirectoryService;
import com.ashwin.messenger.server.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
	private ChatService _chatService;
	
	@Autowired
	private DirectoryService _directoryService;
	
	private final Map<DeferredResult<List<Message>>, Student> _chatRequests = 
			new ConcurrentHashMap<DeferredResult<List<Message>>, Student>();
	
	@RequestMapping(method=RequestMethod.GET, value="{eid}/chat/new")
	public @ResponseBody DeferredResult<List<Message>> getMessages(@PathVariable String eid) {
		Student student = _directoryService.getStudentByEID(eid);
		if(student == null) return null;
		
		final DeferredResult<List<Message>> result = new DeferredResult<List<Message>>(null, Collections.emptyList());
		_chatRequests.put(result, student);
		
		result.onCompletion(new Runnable() {
			@Override
			public void run() {
				_chatRequests.remove(result);
			}
		});
		
		List<Message> messages = _chatService.getNewMessages(student);
		if (messages != null && !messages.isEmpty())
			result.setResult(messages);

		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="{eid}/chat/send")
	public @ResponseBody Message postMessage(@PathVariable String eid, @RequestBody Message message) {
		if(_directoryService.getStudentByEID(eid) == null) return null;
		
		_chatService.sendMessage(message);

		for (Entry<DeferredResult<List<Message>>, Student> entry : _chatRequests.entrySet()) {
			List<Message> messages = _chatService.getNewMessages(entry.getValue());
			entry.getKey().setResult(messages);
		}
		
		return message;
	}

//	@RequestMapping(method=RequestMethod.GET, value="{eid}/conversation/{id}")
//	public @ResponseBody List<Message> getAdditionalMessages(@PathVariable String eid, 
//			@PathVariable int id, @RequestParam("size") int size, @RequestParam("additional") int additional) {
//		Student student = _directoryService.getStudentByEID(eid);
//		if(student == null) return null;
//		
//		return _messengerService.getAdditionalMessages(student, id, size, additional);
//	}
//	
//	@RequestMapping(method=RequestMethod.GET, value="{eid}/conversation")
//	public @ResponseBody List<Conversation> getConversations(@PathVariable String eid) {
//		if(_directoryService.getStudentByEID(eid) == null) return null;
//		
//		return _messengerService.getConversations(eid);
//	}
//	
////	@RequestMapping(method=RequestMethod.GET, value="{eid}/update")
////	public @ResponseBody List<Conversation> getNewConversations(@PathVariable String eid) {
////		Student student = _directoryService.getStudentByEID(eid);
////		if(student == null) return null;
////		
////		List<Conversation> conversations = _messengerService.getNewConversations(student);
////		student.setLastRefreshTime(System.currentTimeMillis());
////		_directoryService.updateStudent(student);
////		
////		return conversations;
////	}
//	
//	@RequestMapping(method=RequestMethod.GET, value="{eid}/update")
//	public @ResponseBody List<Message> getNewMessages(@PathVariable String eid) {
//		Student student = _directoryService.getStudentByEID(eid);
//		if(student == null) return null;
//		
//		List<Message> messages = _messengerService.getNewMessages(student);
//		student.setLastRefreshTime(System.currentTimeMillis());
//		_directoryService.updateStudent(student);
//		
//		return messages;
//	}
//	
//	@RequestMapping(method=RequestMethod.POST, value="{eid}/send")
//	public @ResponseBody Message sendMessage(@PathVariable String eid, @RequestBody Message message) {
//		if(_directoryService.getStudentByEID(eid) == null) return null;
//		return _messengerService.sendMessage(message);
//	}
//	
}
