package com.scheduler.apiPayloads.requests;

import lombok.Data;

@Data
public class AddBlogRequest {
	private String title;
	private String body;
}
