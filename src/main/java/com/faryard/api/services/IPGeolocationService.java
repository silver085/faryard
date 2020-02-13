package com.faryard.api.services;

import com.faryard.api.DTO.node.NodeGeoLocalization;
import com.faryard.api.configurators.Env;
import com.faryard.api.services.impl.node.ActionService;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.InsightsResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Postal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
public class IPGeolocationService {
    @Autowired
    Env env;
    private static final Logger logger = LoggerFactory.getLogger(IPGeolocationService.class);


    public NodeGeoLocalization getLocation(String ipAddress) {
        if(ipAddress == null) return null;
        try (WebServiceClient client = new WebServiceClient.Builder(211364, "cM03L3iiZNbH")
                .build()) {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            InsightsResponse response = client.insights(inetAddress);
            Country country = response.getCountry();
            City city = response.getCity();
            //Postal postal = response.getPostal();

            NodeGeoLocalization localization = new NodeGeoLocalization();
            localization.setCityName(city.getName());
            localization.setCountryName(country.getName());
            //localization.setPostalCode(postal.getCode());

            logger.info("Node ip [{}] - Localized: {} {} {}",ipAddress, localization.getPostalCode(), localization.getCityName(), localization.getCountryName());

            return localization;
        }catch (Exception e){
            logger.error("Geolocation Service error: {} - ipAddr: {}", e.getMessage(), ipAddress);
            return null;
        }
    }
}
