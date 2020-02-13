package com.faryard.api.services;

import com.faryard.api.DTO.IPLookupResponse;
import com.faryard.api.DTO.node.NodeGeoLocalization;
import com.faryard.api.configurators.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class IPGeolocationService {
    @Autowired
    Env env;
    private static final Logger logger = LoggerFactory.getLogger(IPGeolocationService.class);


    public NodeGeoLocalization getLocation(String ipAddress) {
        if(ipAddress.equals("0:0:0:0:0:0:0:1"))
            ipAddress = "213.225.2.120";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity requestEntity = new HttpEntity(getHeaders());
        String uri = env.getProperty("ipstack.url") + ipAddress+ "?access_key=" + env.getProperty("ipstack.apikey");
        ResponseEntity<IPLookupResponse> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, IPLookupResponse.class);
        NodeGeoLocalization localization = new NodeGeoLocalization();
        IPLookupResponse ipLookupResponse = response.getBody();
        if(ipLookupResponse != null){
            localization.setCityName(ipLookupResponse.getCity());
            localization.setCountryName(ipLookupResponse.getCountry_name());
            localization.setPostalCode(ipLookupResponse.getZip());
        }


        return localization;
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // Request to return JSON format
        return headers;
    }
}
