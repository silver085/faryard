package com.faryard.api.controllers;

import com.faryard.api.DTO.GraphicElementsDTO;
import com.faryard.api.DTO.UserNodeDTO;
import com.faryard.api.DTO.UserProfileDTO;
import com.faryard.api.DTO.node.NodeResponseStatus;
import com.faryard.api.DTO.node.NodeSensorStatusResponse;
import com.faryard.api.DTO.node.SensorsStatus;
import com.faryard.api.facades.NodeFacade;
import com.faryard.api.facades.UserFacade;
import com.faryard.api.facades.exception.ExceptionUserNotAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1")
public class ApiBaseController {
    @Autowired
    MessageSource msgSource;

    @Autowired
    UserFacade userFacade;
    @Autowired
    NodeFacade nodeFacade;


    @GetMapping("/test")
    public String testMethod(){

        return msgSource.getMessage("api.testmessage", null, Locale.forLanguageTag(LocaleContextHolder.getLocale().getLanguage()));
    }

    @GetMapping("/isvalidauth")
    public String isValidAuth(){
        return "OK";
    }

    @GetMapping("/userprofile")
    public UserProfileDTO getUserProfile(){
        return userFacade.getLoggedUserProfile();
    }

    @GetMapping("/mynodes")
    public List<UserNodeDTO> getUserNodes(){
        return userFacade.getMyNodes();
    }

    @GetMapping("/nodesensors")
    public NodeSensorStatusResponse getNodeSensors(@RequestParam String nodeId)  {
        try {
            userFacade.isUserAllowedForNode(nodeId);
            return nodeFacade.nodeSensorsStatus(nodeId);
        } catch (ExceptionUserNotAllowed exceptionUserNotAllowed) {
            NodeSensorStatusResponse response = new NodeSensorStatusResponse();
            response.setStatus(NodeResponseStatus.ERROR);
            response.setMessage("User is not allowed");
            return response;
        }

    }
    @GetMapping("/nodegraphs")
    public GraphicElementsDTO getGraph(@RequestParam String nodeId, @RequestParam String timespan, @RequestParam String startDate, @RequestParam String endDate){
        try {
            userFacade.isUserAllowedForNode(nodeId);
            return nodeFacade.buildGraph(nodeId, timespan, startDate, endDate);
        } catch (ExceptionUserNotAllowed exceptionUserNotAllowed) {
            GraphicElementsDTO response = new GraphicElementsDTO();
            response.setStatus(NodeResponseStatus.ERROR.getStatus());
            response.setMessage("User is not allowed");
            return response;
        }
    }

}
