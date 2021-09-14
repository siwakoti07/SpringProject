package com.springbootweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springbootweb.entities.User;

public interface UserReprository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);
	
	
	//@Query("select u from User u where u.role = :role")
	
	public List<User> findByRole(String role);
	
	

	
}
