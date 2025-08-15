package com.example.sportyassignment.unit;

import com.example.sportyassignment.client.AviationAPIClient;
import com.example.sportyassignment.exception.AirportNotFoundException;
import com.example.sportyassignment.exception.BadRequestException;
import com.example.sportyassignment.exception.InternalServerErrorException;
import com.example.sportyassignment.exception.ServiceUnavailableException;
import com.example.sportyassignment.model.AirportDetails;
import com.example.sportyassignment.service.AirportService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AirportServiceTest {

    AviationAPIClient aviationAPIClient = mock(AviationAPIClient.class);

    @Test
    void testGetAirportsSuccess() {
        AirportService service = new AirportService(aviationAPIClient);

        AirportDetails details = new AirportDetails();
        Map<String, List<AirportDetails>> mockResponse = Collections.singletonMap("JFK", List.of(details));
        when(aviationAPIClient.getAirports("JFK")).thenReturn(mockResponse);

        Map<String, List<AirportDetails>> result = service.getAirports("JFK");
        assertEquals(1, result.size());
        assertTrue(result.containsKey("JFK"));
    }

    @Test
    void testGetAirportsNotFound() {
        AirportService service = new AirportService(aviationAPIClient);

        feign.FeignException notFound = mock(feign.FeignException.class);
        when(notFound.status()).thenReturn(404);
        when(aviationAPIClient.getAirports("XXX")).thenThrow(notFound);

        assertThrows(AirportNotFoundException.class, () -> service.getAirports("XXX"));
    }

    @Test
    void testGetAirportsBadRequest() {
        AirportService service = new AirportService(aviationAPIClient);

        feign.FeignException badRequest = mock(feign.FeignException.class);
        when(badRequest.status()).thenReturn(400);
        when(aviationAPIClient.getAirports("BAD")).thenThrow(badRequest);

        assertThrows(BadRequestException.class, () -> service.getAirports("BAD"));
    }

    @Test
    void testGetAirportsInternalServerError() {
        AirportService service = new AirportService(aviationAPIClient);

        feign.FeignException internalError = mock(feign.FeignException.class);
        when(internalError.status()).thenReturn(500);
        when(aviationAPIClient.getAirports("ERR")).thenThrow(internalError);

        assertThrows(InternalServerErrorException.class, () -> service.getAirports("ERR"));
    }

    @Test
    void testGetAirportsServiceUnavailable() {
        AirportService service = new AirportService(aviationAPIClient);

        feign.FeignException unavailable = mock(feign.FeignException.class);
        when(unavailable.status()).thenReturn(503);
        when(aviationAPIClient.getAirports("UNAVAIL")).thenThrow(unavailable);

        assertThrows(ServiceUnavailableException.class, () -> service.getAirports("UNAVAIL"));
    }

    @Test
    void testGetAirportsUnexpectedError() {
        AirportService service = new AirportService(aviationAPIClient);

        feign.FeignException unexpected = mock(feign.FeignException.class);
        when(unexpected.status()).thenReturn(418); // Example of an unexpected status code
        when(aviationAPIClient.getAirports("TEAPOT")).thenThrow(unexpected);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getAirports("TEAPOT"));
        assertTrue(ex.getMessage().contains("Unexpected error"));
    }
}
