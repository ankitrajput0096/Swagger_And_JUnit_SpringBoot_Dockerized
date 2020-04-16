package com.example.Spring_Boot_JPA.serviceInterface;

import java.util.List;

import com.example.Spring_Boot_JPA.model.Topic;

public interface SpringBootServiceInter {
	
	public List<Topic> getAllTopics();
	public Topic getTopic(String id);
	public Topic addTopic(Topic topic);
	public Topic updatetopic(Topic topic,String id);
	public void deletetopic(String id);
}
