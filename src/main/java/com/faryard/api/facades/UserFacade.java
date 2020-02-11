package com.faryard.api.facades;

import com.faryard.api.DTO.UserProfileDTO;
import com.faryard.api.services.impl.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    @Autowired
    MongoUserService userService;

    public UserProfileDTO getLoggedUserProfile() {
        return userService.getLoggedUser();
    }
}
