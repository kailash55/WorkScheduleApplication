package com.scheduler.apiPayloads.responses;

import lombok.Data;

@Data
public class BlogResponse {
	private Long blogId;
	private String title;
	private String body;
	private String createdDate;
}
