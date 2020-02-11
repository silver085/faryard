package com.faryard.api.facades;

import com.faryard.api.DTO.UserNodeDTO;
import com.faryard.api.DTO.UserProfileDTO;
import com.faryard.api.facades.exception.ExceptionUserNotAllowed;
import com.faryard.api.services.impl.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFacade {
    @Autowired
    MongoUserService userService;

    public UserProfileDTO getLoggedUserProfile() {
        return userService.getLoggedUser();
    }

    public List<UserNodeDTO> getMyNodes() {
        return userService.getMyNodes();
    }

    public void isUserAllowedForNode(String nodeId) throws ExceptionUserNotAllowed {
        if(userService.isUserAllowedForNodeId(nodeId)){
            return;
        }
        throw new ExceptionUserNotAllowed();
    }
}
