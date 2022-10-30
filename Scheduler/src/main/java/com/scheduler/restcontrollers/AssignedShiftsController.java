package com.scheduler.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.AssignShiftRequest;
import com.scheduler.services.AssignedShiftService;

@RestController
@RequestMapping("/assigned-shifts")
public class AssignedShiftsController {

	@Autowired
	AssignedShiftService assignedShiftService;
	
	@PostMapping
	public ResponseEntity<?> assignShift(@RequestBody AssignShiftRequest assignShiftRequest)
	{
		try {
			List<Long> ids = assignedShiftService.assignShifts(assignShiftRequest);
			return ResponseEntity.ok(ids);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}