package com.example.Spring_Boot_JPA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.example.Spring_Boot_JPA.model.Topic;
import com.example.Spring_Boot_JPA.service.springBootService;

@RestController
@RequestMapping(value="/springBootJpa")
@Api(value="Simple Spring Controller", description="Simple Spring Controller to do CRUD operations")
public class SpringController {
	
	@Autowired
	private springBootService springbootservice;
	
	@ApiOperation(value = " Hello World rest endpoint ")
	@RequestMapping(value="/",method=RequestMethod.GET, produces = "text/plain")
	public ResponseEntity<String> helloMethod()
	{
		return new ResponseEntity<>("Hello World", HttpStatus.OK);
	}
	
	@ApiOperation(value = " return list of topics from db ")
	@RequestMapping(value="/topics", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Topic>> listOfTopcs()
	{
		return new ResponseEntity<List<Topic>>(springbootservice.getAllTopics(), HttpStatus.OK);
	}
	
	@ApiOperation(value = " return topic from db with specific ID")
	@RequestMapping(value="/topics/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Topic> getRequiredTopic(@PathVariable String id)
	{
		Topic resultTopic = springbootservice.getTopic(id);
		if(resultTopic != null) {
			return new ResponseEntity<Topic>(resultTopic, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	//In this json object is sent 
	/*
	 * {
	 * 		"id":"java"
	 * 		"name":"java programming"
	 * 		"description":"java is easy"
	 * }
	 */
	@ApiOperation(value = "Adding new topic in db")
	@RequestMapping(value="/topics/add",method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addTopic(@RequestBody Topic topic)
	{
		springbootservice.addTopic(topic);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Updating existing topic in db with specific ID")
	@RequestMapping(value="/topics/update/{id}",method=RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateTopic(@RequestBody Topic topic,@PathVariable String id)
	{
		springbootservice.updatetopic(topic,id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//Url "localhost:8080/springBootJpa/topics/delete/java
	@ApiOperation(value = "delete existing topic in db with specific ID")
	@RequestMapping(value="/topics/delete/{id}",method=RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteTopic(@PathVariable String id)
	{
		springbootservice.deletetopic(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//Url "localhost:8080/springBootJpa/topics/getById?id=java
	@ApiOperation(value = " return topic from db with specific ID")
	@RequestMapping(value="/topics/getById",method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Topic> getById(@RequestParam(value="id")String id)
	{
		Topic resultTopic = springbootservice.getById(id);
		if(resultTopic != null) {
			return new ResponseEntity<Topic>(resultTopic, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = " return topic from db with specific ID and Specific Name")
	@RequestMapping(value="/topics/getByIdAndName",method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Topic> getByIdAndName(@RequestParam(value="id")String id,@RequestParam(value="name")String name)
	{
		Topic resultTopic = springbootservice.getByIdAndName(id, name);
		if(resultTopic != null) {
			return new ResponseEntity<Topic>(resultTopic, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
