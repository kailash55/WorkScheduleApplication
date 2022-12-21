package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.AddBlogRequest;
import com.scheduler.apiPayloads.responses.BlogResponse;
import com.scheduler.models.Blog;
import com.scheduler.repository.BlogRepository;
import com.scheduler.util.date.ConversionUtil;

@Service
public class BlogService {

	@Autowired
	BlogRepository blogRepo;
	
	public void saveBlog(AddBlogRequest addBlogRequest)
	{
		Blog blog = new Blog();
		blog.setTitle(addBlogRequest.getTitle());
		blog.setBody(addBlogRequest.getBody());
		blog.setCreatedDateTime(new Date());
		blogRepo.save(blog);
	}
	
	public List<BlogResponse> getAllBlogs()
	{
		List<BlogResponse> responseList = new ArrayList<>();
		List<Blog> blogs = blogRepo.findAll();
		for(Blog blog: blogs)
		{
			BlogResponse response = new BlogResponse();
			response.setBlogId(blog.getId());
			response.setTitle(blog.getTitle());
			response.setBody(blog.getBody());
			response.setCreatedDate(ConversionUtil.toString_ddMMyyyyy(blog.getCreatedDateTime()));
			responseList.add(response);
		}
		return responseList;
	}
	
	public BlogResponse getBlogById(Long blogId)
	{
		Blog blog = blogRepo.findById(blogId).get();
		BlogResponse response = new BlogResponse();
		response.setBlogId(blog.getId());
		response.setTitle(blog.getTitle());
		response.setBody(blog.getBody());
		response.setCreatedDate(ConversionUtil.toString_ddMMyyyyy(blog.getCreatedDateTime()));
		return response;
	}

	public void update(Long id, AddBlogRequest addBlogRequest) {
		// TODO Auto-generated method stub
		Blog blog = blogRepo.findById(id).get();
		blog.setTitle(addBlogRequest.getTitle());
		blog.setBody(addBlogRequest.getBody());
		blogRepo.save(blog);
	}
}
