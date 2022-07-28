package com.scheduler.schedulerpresentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/employerregistration")
public class EmployerRegistrationController {

	@GetMapping()
	public ModelAndView registrationPageLoad() 
	{
		ModelAndView mv = new ModelAndView("registration");
		return mv;
	}
}

