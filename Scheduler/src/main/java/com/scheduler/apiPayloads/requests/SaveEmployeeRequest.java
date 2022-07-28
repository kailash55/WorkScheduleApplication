package com.scheduler.apiPayloads.requests;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SaveEmployeeRequest {
	
    private String firstName;

    private String lastName;
    
    private String email;

    private String mobileNumber;
    
    private Integer weeklyWorkingHours;
    
    private String username;
    
    List<Long> positionIds;
    
    public SaveEmployeeRequest()
    {
    	positionIds = new ArrayList<Long>();
    }
}
