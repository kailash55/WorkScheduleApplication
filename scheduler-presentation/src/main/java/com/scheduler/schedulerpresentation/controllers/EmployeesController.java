package com.scheduler.schedulerpresentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/employees")
public class EmployeesController {

	@GetMapping()
	public ModelAndView employeesPageLoad() 
	{
		ModelAndView mv = new ModelAndView("employees");
		return mv;
	}
}