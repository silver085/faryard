package com.faryard.api.controllers;

import com.faryard.api.DTO.node.NodePingRequest;
import com.faryard.api.DTO.node.NodeSimpleResponse;
import com.faryard.api.DTO.node.RegisterNodeRequest;
import com.faryard.api.facades.NodeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/nodeapi/")
public class NodeController extends ApiBaseController {
    @Autowired
    NodeFacade nodeFacade;

    @PostMapping("/ping")
    public NodeSimpleResponse nodePing(@RequestBody NodePingRequest request){
        return nodeFacade.nodePing(request);
    }

    @PostMapping("/registernode")
    public NodeSimpleResponse registerNode(@RequestBody RegisterNodeRequest request){
        return nodeFacade.registerNode(request);
    }

}
