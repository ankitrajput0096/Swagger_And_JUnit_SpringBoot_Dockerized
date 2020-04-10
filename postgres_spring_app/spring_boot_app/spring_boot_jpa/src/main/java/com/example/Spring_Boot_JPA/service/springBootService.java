package com.example.Spring_Boot_JPA.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring_Boot_JPA.model.Topic;
import com.example.Spring_Boot_JPA.repository.TopicRepository;
import com.example.Spring_Boot_JPA.serviceInterface.SpringBootServiceInter;

@Service
public class springBootService implements SpringBootServiceInter{

	@Autowired
	private TopicRepository topicRepository;
	
	/*private ArrayList<Topic> topics=new ArrayList<>();
	
	public springBootService()
	{
		topics.add(new Topic("spring","springFramework","spring framework is awesome"));
	
		topics.add(new Topic("java","core java","java is very best in it's field"));
	
		topics.add(new Topic("javascript","basic javascript","javascript is not so good"));
	}
	*/
	public List<Topic> getAllTopics()
	{
		//return topics;
		return topicRepository.findAll();
	}
	public Topic getTopic(String id)
	{
		/*Topic topic=null;
		for(Topic t:topics)
		{
			if(t.getId().equals(id))
			{
				topic=t;
			}
		}
		return topic;
		*/
		
		return topicRepository.findOne(id);
	}
	public void addTopic(Topic topic)
	{
		//topics.add(topic);
		topicRepository.save(topic);
	}
	public void updatetopic(Topic topic,String id)
	{
		/*int count=0;
		for(Topic t:topics)
		{
			if(t.getId().equals(id))
			{
				break;
			}
			else
				count+=1;
		}
		topics.set(count, topic);
		*/
		topicRepository.delete(id);
		topicRepository.save(topic);
	}
	public void deletetopic(String id)
	{
		/*int count=0;
		for(Topic t:topics)
		{
			if(t.getId().equals(id))
			{
				break;
			}
			else
				count+=1;
		}
		topics.remove(count);	
		*/
		topicRepository.delete(id);
	}
	
	public Topic getById(String id)
	{
		return topicRepository.getById(id);
	}
	
	public Topic getByIdAndName(String id,String name)
	{
		return topicRepository.getByIdAndName(id, name);
	}
	
}
