package com.jpa.StudentManagementJpa.dao;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.jpa.StudentManagementJpa.dto.User;

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
public class UserService {
	@Autowired
	UserRepository userRepo;

	public List<User> selectAllUser() {
		List<User> list = (List<User>) userRepo.findAll();
		return list;
	}

	public Optional<User> getUserByUserid(String id){
		return userRepo.findById(id);
	}
	
	public User getUser(String id) {
		return userRepo.findById(id).get();
	}

	public User save(User user) {
		return userRepo.save(user);
	}

	public void delete(String id) {
		userRepo.deleteById(id);
	}

	public User update(User user, String id) {
		return userRepo.save(user);
	}

	public List<User> searchUser(String id, String name) {
		List<User> list = userRepo.searchUser(id, name);
		return list;
	}

	public User validateLogin(String id, String password) {
		User u = new User();
		List<User> list = this.selectAllUser();
		if (list != null) {
			for (User user : list) {
				if (user.getId().equals(id) && user.getPassword().equals(password)) {

					u.setId(user.getId());
					u.setName(user.getPassword());
				}
			}
		}
		return u;
	}
	
	 public  String userIDGenerator() {
		 String id = ""; 
		  List<User> list = this.selectAllUser();
		  if(list == null || list.size() <= 0) { 
			  id = "USR001"; 
		  }else {
			  User lastDTO = list.get(list.size()-1); 
			  System.out.println(lastDTO.getId());
			  int lastId = Integer.parseInt(lastDTO.getId().substring(3)); 
			  id = String.format("USR"+"%03d", lastId+1); 
			  }
		return id; 
	 }
	 
	    public void generateHTMLReport() throws JRException, IOException{
	    	String path="C:\\hello";
		    List<User> users=(List<User>) userRepo.findAll();
		    System.out.println(users.toString());
		    File file=ResourceUtils.getFile("classpath:user.jrxml");
	        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(users);
	        Map<String,Object>  parameters=new HashMap<>();
	        parameters.put("createdBy", "Admin");
	        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
	        JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"/user.html");	
	    }
	    
	    public void generatePDFReport() throws JRException, IOException{
			String path="C:\\hello";
		    List<User> users=(List<User>) userRepo.findAll();
		    File file=ResourceUtils.getFile("classpath:user.jrxml");
	        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(users);
	        Map<String,Object>  parameters=new HashMap<>();
	        parameters.put("createdBy", "Admin");
	        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/user.pdf");	
	    }
	    
	    public void generateExcelReport(HttpServletResponse response) throws JRException, IOException{
	    	String path="C:\\hello";
		    List<User> users=(List<User>) userRepo.findAll();
		    System.out.println(users.toString());
		    File file=ResourceUtils.getFile("classpath:user.jrxml");
	        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(users);
	        Map<String,Object>  parameters=new HashMap<>();
	        parameters.put("createdBy", "Admin");
	        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
	        JRXlsxExporter exporter = new JRXlsxExporter();
	        exporter.setExporterInput( new SimpleExporterInput(jasperPrint));
	        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(path + "/user.xlsx" ));

	        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
	        config.setOnePagePerSheet( true );
	        config.setDetectCellType( true );
	        exporter.setConfiguration( config );
	        exporter.exportReport();
	    }

}
