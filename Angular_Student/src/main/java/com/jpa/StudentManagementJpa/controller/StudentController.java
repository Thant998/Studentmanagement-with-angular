
package com.jpa.StudentManagementJpa.controller;


import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jpa.StudentManagementJpa.dao.CourseService;
import com.jpa.StudentManagementJpa.dao.StudentService;
import com.jpa.StudentManagementJpa.dto.Course;
import com.jpa.StudentManagementJpa.dto.Student;
import com.jpa.StudentManagementJpa.model.StudentBean;

import net.sf.jasperreports.engine.JRException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

 @RequestMapping("/api/v1/") 
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@ModelAttribute("studentBean")
	public StudentBean getStudentBean() {
		return new StudentBean();
	}
	
	@ModelAttribute(value = "courseList")
	public List<Course> courseList() {
		ArrayList<String> list = new ArrayList<>();
		List<Course> courseLists = courseService.selectAllCourse();
		for (Course course : courseLists) {
			list.add(course.getName());
		}
		return courseLists;
	}
	
	 @GetMapping(value = "/student/{id}/{name}/{course}", produces = "application/json")
	    public List<Student> searchStudent(@PathVariable String id,@PathVariable String name,@PathVariable String course) {
	    	List<Student> list=studentService.searchStudent(id,name,course);
	    	return list;
	  }

	@GetMapping(value = "/student", produces = "application/json")
	public List<Student> selectAllStudent() {
		return studentService.selectAllStudent();
	}

	@PostMapping(value = "/student", produces = "application/json")
	public Student addStudent(@RequestBody Student student) {
		Student dto = new Student();
		dto.setId(studentService.studentIDGenerator());
		dto.setName(student.getName());
		dto.setDob(student.getDob());
		dto.setGender(student.getGender());
		dto.setEducation(student.getEducation());
		dto.setPhone(student.getPhone());
		ArrayList<Course> courses = (ArrayList<Course>) student.getCourses();
		for (Course c : courses) {
			dto.addCourse(c);
		}
		return studentService.save(dto);
	}
	
	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable String id) {
		Student student = new Student();
		student = studentService.getById(id);
		return ResponseEntity.ok(student);
	}

	@PutMapping(value = "/student/{id}", produces = "application/json")
	public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student studentDetails) {
		Student student = new Student();
		student = studentService.getById(id);
		student.setId(studentDetails.getId());
		student.setName(studentDetails.getName());
		student.setDob(studentDetails.getDob());
		student.setGender(studentDetails.getGender());
		student.setEducation(studentDetails.getEducation());
		student.setPhone(studentDetails.getPhone());
		if(!duplicateCourse(studentDetails.getCourses())) {
			student.setCourses(studentDetails.getCourses());
		}
        System.out.println(student.getCourses().toString());
		Student updatedStudent = studentService.update(student);
		return ResponseEntity.ok(updatedStudent);
	}
	public static boolean duplicateCourse(List<Course> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(i).getId().equals(list.get(j).getId()) && i != j) {
                    return true;
                }
            }
        }
        return false;
    }
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable String id) {
		studentService.deleteById(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("student/export/html")
	public  void generateHTMLReport() throws JRException, IOException{    
		studentService.generateHTMLReport();
	}
	
	@GetMapping("student/export/pdf")
	public  void generatePDFReport() throws JRException, IOException{    
		studentService.generatePDFReport();
	}
	
	@GetMapping("student/export/excel")
	public  void generateExcelReport(HttpServletResponse response) throws JRException, IOException{    
		studentService.generateExcelReport(response);
	}


}
