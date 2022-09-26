package com.jpa.StudentManagementJpa.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jpa.StudentManagementJpa.dao.CourseService;
import com.jpa.StudentManagementJpa.dto.Course;


@CrossOrigin(origins = "http://localhost:4200")
@RestController

@RequestMapping("/api/v1/")
public class CourseController {

	@Autowired
	private CourseService courseService;
	

	@GetMapping(value = "/course", produces = "application/json")
	public List<Course> selectAllCourse() {
		return courseService.selectAllCourse();
	}

	@PostMapping(value = "/course", produces = "application/json")
	public Course addCourse(@RequestBody Course course) {
		Course dto=new Course();
		dto.setId(courseService.courseIDGenerator());
		dto.setName(course.getName());
		return courseService.save(dto);
	}
	
	@GetMapping("/course/{name}")
	public ResponseEntity<Course> getCourseByName(@PathVariable String name) {
		Course course = new Course();
		course = courseService.getCoursebyName(name);
		return ResponseEntity.ok(course);
	}	
}
