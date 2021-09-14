package com.springbootweb.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springbootweb.dao.UserReprository;
import com.springbootweb.entities.Contact;
import com.springbootweb.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserReprository userReprository;
	
	@ModelAttribute
	public void genetalMethod(Model model, Principal principal ) {
		String name = principal.getName();
		System.out.println("USERNAME "+name);
		
		User user = userReprository.getUserByUserName(name);
		System.out.println("USER: "+user);
		
		model.addAttribute(user);
		
		
	}
	
	
	@RequestMapping("/index")
	public String user_dashboard(Model model, Principal principal)
	{
		model.addAttribute("title", "UserDashbord");
		return "normal/user_index";
	}
	
	
	@RequestMapping("/")
	public String user_dashboard1(Model model, Principal principal)
	{
		return "normal/user_index";
	}
	
	//open add form handeller 
	
	@GetMapping("/add-contact")
	public String open_addContactForm(Model model)
	{
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_conact_form";
	}
	
	
	//processing add contact form
	
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, Principal principal)
	{
		
		String name = principal.getName();
		User user = userReprository.getUserByUserName(name);
		
		contact.setUser(user);
		user.getList().add(contact);
		
		userReprository.save(user);
		
		//System.out.println("DATA "+ contact);
		return "normal/add_conact_form";
	}
	
	
	

}
