package com.scheduler.schedulerpresentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shifts")
public class ShiftsController {

	@GetMapping()
	public ModelAndView shiftsPageLoad() 
	{
		ModelAndView mv = new ModelAndView("shifts");
		return mv;
	}
	
}
