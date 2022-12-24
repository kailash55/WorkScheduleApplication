package com.stafferin.landingpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping("/")
	public ModelAndView login()
	{
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@GetMapping("/blog")
	public ModelAndView blogs()
	{
		ModelAndView mv = new ModelAndView("blog");
		return mv;
	}
	
	@GetMapping("/blog-single")
	public ModelAndView blogRead()
	{
		ModelAndView mv = new ModelAndView("blog-single");
		return mv;
	}
	
	@GetMapping("/blog-list")
	public ModelAndView blogList()
	{
		ModelAndView mv = new ModelAndView("blog-list");
		return mv;
	}
	
	@GetMapping("/blog-editor")
	public ModelAndView blogEditor()
	{
		ModelAndView mv = new ModelAndView("blog-editor");
		return mv;
	}
}
