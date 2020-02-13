package com.faryard.api.controllers;


import com.faryard.api.DTO.MarkActionDoneRequest;
import com.faryard.api.DTO.node.NodeExecuteAction;
import com.faryard.api.DTO.node.*;
import com.faryard.api.facades.NodeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/nodeapi/")
public class NodeController extends ApiBaseController {
    @Autowired
    NodeFacade nodeFacade;

    @PostMapping("/ping")
    public NodeSimpleResponse nodePing(@RequestBody NodePingRequest request, HttpServletRequest servletRequest){
        return nodeFacade.nodePing(request, servletRequest);
    }

    @PostMapping("/registernode")
    public NodeSimpleResponse registerNode(@RequestBody RegisterNodeRequest request){
        return nodeFacade.registerNode(request);
    }

    @GetMapping("/existnode")
    public NodeSimpleResponse existNode(@RequestParam String macAddress){
        return nodeFacade.existNode(macAddress);
    }

    @GetMapping("/status")
    public NodeStatusResponse nodeStatus(@RequestParam String macAddress){
        return nodeFacade.nodeStatus(macAddress);
    }

    @GetMapping("/statusbyid")
    public NodeStatusResponse nodeStatusById(@RequestParam String nodeId){
        return nodeFacade.nodeStatusById(nodeId);
    }

    @GetMapping("/sensors")
    public NodeSensorStatusResponse nodeSensorStatus(@RequestParam String nodeId){
        return nodeFacade.nodeSensorsStatus(nodeId);
    }


    @PostMapping("/doaction")
    public NodeSimpleResponse doAction(@RequestBody NodeExecuteAction nodeAction ){
        return nodeFacade.doAction(nodeAction);
    }

    @PostMapping("/actiondone")
    public NodeSimpleResponse markActionDone(@RequestBody MarkActionDoneRequest actionDoneRequest){
        return nodeFacade.markActionAsDone(actionDoneRequest);
    }
}
