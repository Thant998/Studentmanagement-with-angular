package com.jpa.StudentManagementJpa.dao;

import java.util.List;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jpa.StudentManagementJpa.dto.User;


public interface UserRepository  extends CrudRepository<User, String> {
    @Query(value="select * from user where id=:id or name=:name",nativeQuery = true)
	List<User> searchUser(@Param("id") String id, @Param("name") String name);
    
    @Query(value="select * from user where id=:id",nativeQuery = true)
    User searchbyId(@Param("id") String id);
 
  
  
}
