package com.scheduler.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.AddBlogRequest;
import com.scheduler.services.BlogService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/blogs")
public class BlogController {

	@Autowired
	BlogService blogService;
	
	@PostMapping
	public ResponseEntity<?> addNewBlog(@RequestBody AddBlogRequest addBlogRequest)
	{
		blogService.saveBlog(addBlogRequest);
		return ResponseEntity.ok("New blog added.");
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateBlog(@PathVariable("id") Long id,@RequestBody AddBlogRequest addBlogRequest)
	{
		blogService.update(id, addBlogRequest);
		return ResponseEntity.ok("Blog updated.");
	}
	
	@GetMapping
	public ResponseEntity<?> getAllBlogs()
	{
		return ResponseEntity.ok(blogService.getAllBlogs());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getBlog(@PathVariable("id") Long id)
	{
		return ResponseEntity.ok(blogService.getBlogById(id));
	}
}
