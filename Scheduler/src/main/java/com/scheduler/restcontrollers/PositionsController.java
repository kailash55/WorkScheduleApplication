package com.scheduler.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.SavePositionRequest;
import com.scheduler.apiPayloads.responses.FetchResponse;
import com.scheduler.services.PositionService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/positions")
public class PositionsController {

	@Autowired
	PositionService positionService;
	
    @GetMapping
    public ResponseEntity<?> getAllPositions()
    {
        return ResponseEntity.ok(positionService.getPositionsList());
    }

    @PostMapping
    public ResponseEntity<?> savePosition(@RequestBody SavePositionRequest savePositionRequest)
    {
    	Long positionId = positionService.savePostion(savePositionRequest);
        return ResponseEntity.ok(positionId);
    }
}
