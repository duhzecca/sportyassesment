package com.example.sportyassignment.service;

import com.example.sportyassignment.client.AviationAPIClient;
import com.example.sportyassignment.exception.AirportNotFoundException;
import com.example.sportyassignment.exception.BadRequestException;
import com.example.sportyassignment.exception.InternalServerErrorException;
import com.example.sportyassignment.exception.ServiceUnavailableException;
import com.example.sportyassignment.model.AirportDetails;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportService {

    private final AviationAPIClient aviationAPIClient;

    public Map<String, List<AirportDetails>> getAirports(String airportCode) {
        try {
            log.info("Fetching airport details for code: {}", airportCode);
            Map<String, List<AirportDetails>> airports = aviationAPIClient.getAirports(airportCode);
            log.info("Successfully fetched airport details for code: {}", airportCode);
            return airports;
        } catch (FeignException feignException) {
            switch (feignException.status()) {
                case 404:
                    log.error("Airport not found for code: {}", airportCode, feignException);
                    throw new AirportNotFoundException("Airport with code " + airportCode + " not found.");
                case 400:
                    log.error("Bad request for airport code: {}", airportCode, feignException);
                    throw new BadRequestException("Bad request for airport code " + airportCode);
                case 500:
                    log.error("Internal server error while fetching airport data for code: {}", airportCode, feignException);
                    throw new InternalServerErrorException("Internal server error while fetching airport data.");
                case 503:
                    log.error("Service unavailable while fetching airport data for code: {}", airportCode, feignException);
                    throw new ServiceUnavailableException("Service unavailable. Try again later.");
                default:
                    log.error("Unexpected error while fetching airport data for code: {}", airportCode, feignException);
                    throw new RuntimeException("Unexpected error: " + feignException.status());
            }
        }
    }

}
