package com.jpa.StudentManagementJpa.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpa.StudentManagementJpa.dto.Course;

@Service
public class CourseService {
	@Autowired 
	CourseRepository repo;
	
	public List<Course> selectAllCourse(){
		return (List<Course>) repo.findAll();
	}
	
	public Course getCourse(String id) {
		return repo.findById(id).get();
	}
	public Course getCoursebyName(String name) {
		return repo.findByName(name);
	}
	public Course save(Course course) {
		return repo.save(course);
	}
	public boolean isCourseExist(String name) {
		  Course course = this.findByCoureName(name);
		  if(course != null) { 
			 return true;
			  }
		  return false;
	  }
	public String courseIDGenerator() {
		// id auto generate
		 String id = ""; 
		  List<Course> list = this.selectAllCourse();
		  if(list == null || list.size() <= 0) { 
			  id = "COU001"; 
		  }else {
			  Course lastDTO = list.get(list.size()-1); 
			  System.out.println(lastDTO.getId());
			  int lastId = Integer.parseInt(lastDTO.getId().substring(3)); 
			  id = String.format("COU"+"%03d", lastId+1); 
			  }
		return id; 
		  
	}
	public Course findByCoureName(String course) {
		return repo.findByName(course);
	}
}
