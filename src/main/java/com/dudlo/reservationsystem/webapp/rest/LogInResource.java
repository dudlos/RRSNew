package com.dudlo.reservationsystem.webapp.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dudlo.reservationsystem.model.RoleEnum;
import com.dudlo.reservationsystem.model.User;
import com.dudlo.reservationsystem.service.UserService;

@RestController
@RequestMapping
public class LogInResource {

	private UserService userService;
	private Logger log = LoggerFactory.getLogger(LogInResource.class);

	@Autowired
	private Environment env;

	@Autowired
	public LogInResource(UserService userService) {
		this.userService = userService;
	}
/*	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}*/
	
/*	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;
		}
		*/

	
	/*@RequestMapping(value = { "/login" }, method = RequestMethod.POST)//returns userID of the user
	public void Login(
			@RequestParam(value = "userName") String userName, 
			@RequestParam(value = "password") String password)
			 {
		Long userID =	userService.login(userName, password);
		
		
	}*/
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		
	    String referrer = request.getHeader("Referer");
	    if(referrer!=null){
	        request.getSession().setAttribute("url_prior_login", referrer);
	    }
	    ModelAndView model = new ModelAndView();
	    model.addObject(referrer, request);
	    model.setViewName("login");
	    return model;
	}

	@RequestMapping(value = { "/createUser" }, method = RequestMethod.POST)
	public User createUser(
			@RequestParam(value = "name") String firstName, 
			@RequestParam(value = "surname") String lastName,
			@RequestParam(value = "userName") String userName, 
			@RequestParam(value = "password") String password,
			@RequestParam(value = "ROLE", required = false) RoleEnum role
			) 
	{
		User newUser = userService.createUser(firstName, lastName, userName, password, role);
		return newUser;
		
	}

	
	
	
}
