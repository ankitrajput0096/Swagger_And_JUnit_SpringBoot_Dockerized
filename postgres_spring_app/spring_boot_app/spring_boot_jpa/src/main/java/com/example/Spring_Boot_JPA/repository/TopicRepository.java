package com.example.Spring_Boot_JPA.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Spring_Boot_JPA.model.Topic;

public interface TopicRepository
		extends JpaRepository<Topic,String> {
	
	//This is query is know as JPQL (Java Persistent Query Language)
	@Query("SELECT top FROM Topic top WHERE top.id=:id")
	public Topic getById(@Param("id") String id);
	
	@Query("SELECT t FROM Topic t WHERE t.id=:id AND t.name=:name")
	public Topic getByIdAndName(
			@Param("id") String id,
			@Param("name") String name);
}
