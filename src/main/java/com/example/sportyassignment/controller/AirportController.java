package com.example.sportyassignment.controller;

import com.example.sportyassignment.model.AirportDetails;
import com.example.sportyassignment.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/airports")
    @Operation(
            summary = "Get airport details",
            description = "Fetches details for a given airport code"
    )
    @Parameter(
            name = "apt",
            description = "Airport code",
            required = true
    )
    public ResponseEntity<Map<String, List<AirportDetails>>> getAirports(@RequestParam("apt") String airportCode) {
        log.info("Fetching airport details for code: {}", airportCode);
        return ResponseEntity.ok(airportService.getAirports(airportCode));
    }

}
