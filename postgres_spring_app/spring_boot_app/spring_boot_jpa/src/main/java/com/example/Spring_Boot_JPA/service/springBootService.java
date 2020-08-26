package com.example.Spring_Boot_JPA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Spring_Boot_JPA.model.Topic;
import com.example.Spring_Boot_JPA.repository.TopicRepository;
import com.example.Spring_Boot_JPA.serviceInterface.SpringBootServiceInter;

@Service
public class springBootService
		implements SpringBootServiceInter {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopic(String id) {
        return topicRepository.findOne(id);
    }

    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic updatetopic(Topic topic, String id) {
        topicRepository.delete(id);
        return topicRepository.save(topic);
    }

    public void deletetopic(String id) {
        topicRepository.delete(id);
    }

    public Topic getById(String id) {
        return topicRepository.getById(id);
    }

    public Topic getByIdAndName(String id, String name) {
        return topicRepository.getByIdAndName(id, name);
    }
}
