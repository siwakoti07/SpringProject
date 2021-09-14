package com.springbootweb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springbootweb.dao.UserReprository;
import com.springbootweb.entities.User;

public class UserDetailServiceImplement implements UserDetailsService {

	@Autowired
	private UserReprository userReprository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database 
		
		User user = userReprository.getUserByUserName(username);
		
		if(user == null)
		{
			throw new UsernameNotFoundException("Could not found any user");
		}
		
		CustomUserDetails customUserDetails =  new CustomUserDetails(user);
		
		return customUserDetails;
	}

}
