package com.scheduler.schedulerpresentation.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class NullSessionRedirectFilter implements Filter{

	String[] staticSources = {"assets","lib"};
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		Cookie[] cookies = httpRequest.getCookies();
		String authTokenCookieName = "AUTH_TOKEN_COOKIE";
		
		boolean containsStaticPath = Arrays.stream(staticSources)
				.anyMatch(staticSourcePath->httpRequest.getRequestURL().toString().contains(staticSourcePath));
		
		if(!(httpRequest.getRequestURL().toString().contains("login") 
		  || httpRequest.getRequestURL().toString().contains("employerregistration")
		  || containsStaticPath))
		{
			if(cookies!=null)
			{
				Optional<String> authToken = Arrays.stream(cookies)
					    .filter(cookie->authTokenCookieName.equals(cookie.getName()))
					    .map(Cookie::getValue)
					    .findAny();
				if(authToken.isEmpty())
				{
					httpResponse.sendRedirect("login");
					return;
				}
			}
			else
			{
				httpResponse.sendRedirect("login");
				return;
			}
		}
		
		chain.doFilter(httpRequest, httpResponse);
	}

}
