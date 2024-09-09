package com.example.Spring_Boot_JPA.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/simpleController")
@Api(value="Simple Controller")
public class SimpleController {
	
	@ApiOperation(value = " Hello friends rest endpoint ")
	@RequestMapping(
			value="/helloFriends",
			method=RequestMethod.GET,
			produces = "text/plain")
	public ResponseEntity<String> helloFriendsMethod()
	{
		return ResponseEntity.ok().body("hello friends");
	}
}
