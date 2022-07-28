package com.scheduler.schedulerpresentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("schedule")
public class SchedulerController {
	
	@GetMapping
	public ModelAndView pageLoad()
	{
		ModelAndView mv = new ModelAndView("schedule");
		return mv;
	}
}
