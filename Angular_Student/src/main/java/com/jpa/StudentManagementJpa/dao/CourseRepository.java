package com.jpa.StudentManagementJpa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jpa.StudentManagementJpa.dto.Course;


public interface CourseRepository extends CrudRepository<Course, String> {
	@Query(value = "select * from course where name=?1", nativeQuery = true)
	Course findByName(String name);
}
