package com.jpa.StudentManagementJpa.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.jpa.StudentManagementJpa.dto.Student;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;



@Service
public class StudentService {
	@Autowired
	StudentRepository repo;
	
	public Student save(Student s) {
		return repo.save(s);
	}
	
	public Student getById(String id) {
		return repo.findById(id).get();
	}
	
	
	public List<Student> selectAllStudent(){
		return (List<Student>) repo.findAll();
	}
	
	public Student update(Student s) {
		return repo.save(s);
	}
	
	public void deleteById(String id) {
		repo.deleteById(id);
	}
	
	public List<Student> searchStudent(String id, String name, String course){
		return repo.findDistinctStudentByIdOrNameOrCourses_Name(id, name, course);
	}
	public String studentIDGenerator() {
		// id auto generate
		 String id = ""; 
		  List<Student> list = this.selectAllStudent();
		  if(list == null || list.size() <= 0) { 
			  id = "STU001"; 
		  }else {
			  Student lastDTO = list.get(list.size()-1); 
			  int lastId = Integer.parseInt(lastDTO.getId().substring(3)); 
			  id = String.format("STU"+"%03d", lastId+1); 
			  }
		return id; 
		  
	}
	
	 public void generateHTMLReport() throws JRException, IOException{
	    	String path="C:\\hello";
		    List<Student> students=(List<Student>) repo.findAll();
		    System.out.println(students.toString());
		    File file=ResourceUtils.getFile("classpath:student.jrxml");
	        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(students);
	        Map<String,Object>  parameters=new HashMap<>();
	        parameters.put("createdBy", "Admin");
	        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
	        JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"/student.html");	    	
	    }
	 
	  public void generatePDFReport() throws JRException, IOException{
	    	String path="C:\\hello";
		    List<Student> students=(List<Student>) repo.findAll();
		    System.out.println(students.toString());
		    File file=ResourceUtils.getFile("classpath:student.jrxml");
	        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(students);
	        Map<String,Object>  parameters=new HashMap<>();
	        parameters.put("createdBy", "Admin");
	        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/student.pdf");	    	
	    }
	  
	  public void generateExcelReport(HttpServletResponse response) throws JRException, IOException{
	    	String path="C:\\hello";
		    List<Student> students=(List<Student>) repo.findAll();
		    System.out.println(students.toString());
		    File file=ResourceUtils.getFile("classpath:student.jrxml");
	        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(students);
	        Map<String,Object>  parameters=new HashMap<>();
	        parameters.put("createdBy", "Admin");
	        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
	        JRXlsxExporter exporter = new JRXlsxExporter();
	        exporter.setExporterInput( new SimpleExporterInput(jasperPrint));
	        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(path + "/student.xlsx" ));

	        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
	        config.setOnePagePerSheet( true );
	        config.setDetectCellType( true );
	        exporter.setConfiguration( config );
	        exporter.exportReport();
	    }
	  
	  
}
