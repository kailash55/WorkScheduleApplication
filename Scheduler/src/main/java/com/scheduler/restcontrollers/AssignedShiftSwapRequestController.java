package com.scheduler.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.AssignShiftRequest;
import com.scheduler.apiPayloads.requests.AssignedShiftSwapRequestBody;
import com.scheduler.services.AssignedShiftSwapRequestService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/assigned-shift-swap-requests")
public class AssignedShiftSwapRequestController {
	
	@Autowired
	AssignedShiftSwapRequestService swapRequestService;
	
	@PostMapping
	public ResponseEntity<?> assignShift(@RequestBody AssignedShiftSwapRequestBody assignShiftSwapRequest)
	{
		swapRequestService.raiseAssignedShiftSwapRequest(assignShiftSwapRequest);
		return ResponseEntity.ok("Swap request raised");
	}
}
