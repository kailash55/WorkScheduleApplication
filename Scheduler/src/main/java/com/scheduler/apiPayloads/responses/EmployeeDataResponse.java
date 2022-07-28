package com.scheduler.apiPayloads.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeDataResponse {
	private Long id;
	
	private String firstName;

    private String lastName;
    
    private String email;

    private String mobileNumber;
    
    private Integer weeklyWorkingHours;
    
    private String username;
    
    private List<PositionListItem> positions;
    
    public EmployeeDataResponse()
    {
    	positions = new ArrayList<PositionListItem>();
    }
}
