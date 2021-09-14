package com.springbootweb.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootweb.dao.UserReprository;
import com.springbootweb.entities.User;
import com.springbootweb.helper.Message;

@Controller
public class BasicController {
	
	@Autowired
	private UserReprository userReprository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@GetMapping("/") 
	public String homeHandeller(Model model)
	{
		
		model.addAttribute("title", "Home Smart Contact");
		return "home";
	}
	
	@GetMapping("/about")
	public String aboutHandeller(Model model)
	{
		
		model.addAttribute("title", "About Smart Contact");
		return "about";
	}
	
	@GetMapping("/signup")
	public String registerHandeller(Model model)
	{
		
		model.addAttribute("title", "SignUp Smart Contact");
		model.addAttribute("user", new User());		
		return "signup";	
	}
	
	//handel for registering user 
	
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult , @RequestParam(value="agreement"
			,defaultValue="false") boolean agreement, Model model,HttpSession httpSession)
		{
			try {
				
				
	
				if(!agreement)
				{
					System.out.println("You have not agreed the terms and condition");
					throw new Exception("You have not agreed the terms and condition");
				}
				
				if(bindingResult.hasErrors())
				{
					System.out.println("ERRORS "+bindingResult.toString());
					model.addAttribute("user", user);
					return "signup";
				}
				
				user.setRole("ROLE_USER");
				user.setEnabled(true);
				user.setImageUrl("default.png");
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
				
				
				
				model.addAttribute("user", user);
				
				User result = userReprository.save(user);
				
				System.out.println( "AGREEMENT: "+agreement);
				System.out.println("USER: " +result);
				httpSession.setAttribute("message", new Message("Successfully Registered!!!", "alert-success"));
				return  "signup";
				
			} catch (Exception e) {
				
				
				e.printStackTrace();
				model.addAttribute("user", user);
			   httpSession.setAttribute("message", new Message("Something Went Wrong!!"+ e.getMessage(), "alert-danger"));
				return "signup";
				
			}
	}

	//handeller for custom login 
	
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		
		model.addAttribute("title", "Login- Smart Contact");
		return "login";
	}
	
	
	@GetMapping("/role")
	public String role()
	{
		List<User> findByRole = userReprository.findByRole("ROLE_ADMIN");
		
		for (User user : findByRole) {
			System.out.println(user);
			
		}
		
		System.out.println("_______________________________________");
		
		List<User> findByRole2 = userReprository.findByRole("ROLE_USER");
		
		for (User user : findByRole2) {
			System.out.println(user);
		}
		
		return "login";
	}
	@GetMapping("/login-fail")
	public String kk()
	{
		return "login-fail";
	}
}
