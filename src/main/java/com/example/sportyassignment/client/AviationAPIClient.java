package com.example.sportyassignment.client;

import com.example.sportyassignment.model.AirportDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(url = "${aviation-api.base-url}", name = "aviation-api-client")
public interface AviationAPIClient {

    @GetMapping("/airports")
    @CircuitBreaker(name = "aviationApi", fallbackMethod = "fallbackAirportDetails")
    @Retry(name = "aviationApi")
    Map<String, List<AirportDetails>> getAirports(@RequestParam("apt") String airportCode);


    default Map<String, List<AirportDetails>> fallbackAirportDetails(String airportCode, Throwable t) {
        AirportDetails fallbackDetail = new AirportDetails();
        fallbackDetail.setSiteNumber("Unknown");
        fallbackDetail.setType("Unknown");
        fallbackDetail.setFacilityName("Unknown");
        fallbackDetail.setFaaIdent("Unknown");
        fallbackDetail.setIcaoIdent(airportCode);
        fallbackDetail.setRegion("Unknown");
        fallbackDetail.setDistrictOffice("Unknown");
        fallbackDetail.setState("Unknown");
        fallbackDetail.setStateFull("Unknown");
        fallbackDetail.setCounty("Unknown");
        fallbackDetail.setCity("Unknown");
        fallbackDetail.setOwnership("Unknown");
        fallbackDetail.setUse("Unknown");
        fallbackDetail.setManager("Unknown");
        fallbackDetail.setManagerPhone("Unknown");
        fallbackDetail.setLatitude("Unknown");
        fallbackDetail.setLatitudeSec("Unknown");
        fallbackDetail.setLongitude("Unknown");
        fallbackDetail.setLongitudeSec("Unknown");
        fallbackDetail.setElevation("Unknown");
        fallbackDetail.setMagneticVariation("Unknown");
        fallbackDetail.setTpa("Unknown");
        fallbackDetail.setVfrSectional("Unknown");
        fallbackDetail.setBoundaryArtcc("Unknown");
        fallbackDetail.setBoundaryArtccName("Unknown");
        fallbackDetail.setResponsibleArtcc("Unknown");
        fallbackDetail.setResponsibleArtccName("Unknown");
        fallbackDetail.setFssPhoneNumber("Unknown");
        fallbackDetail.setFssPhoneNumerTollfree("Unknown");
        fallbackDetail.setNotamFacilityIdent("Unknown");
        fallbackDetail.setStatus("Data not available");
        fallbackDetail.setCertificationTypedate("Unknown");
        fallbackDetail.setCustomsAirportOfEntry("Unknown");
        fallbackDetail.setMilitaryJointUse("Unknown");
        fallbackDetail.setMilitaryLanding("Unknown");
        fallbackDetail.setLightingSchedule("Unknown");
        fallbackDetail.setBeaconSchedule("Unknown");
        fallbackDetail.setControlTower("Unknown");
        fallbackDetail.setUnicom("Unknown");
        fallbackDetail.setCtaf("Unknown");
        fallbackDetail.setEffectiveDate("Unknown");

        return Map.of("fallback", List.of(fallbackDetail));
    }
}
