package com.faryard.api.services.impl.node;

import com.faryard.api.domain.node.Node;
import com.faryard.api.domain.node.NodeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class NodeScheduler {
    @Autowired
    NodeService nodeService;


    @Scheduled(fixedDelay = 60 * 1000) //Set to 1 min
    private void nodesVitalityCheckJob(){
        List<Node> expiredNodes = nodeService.getExpiredNodes();
        for(Node node : expiredNodes){
            node.setNodeStatus(NodeStatus.Offline);
            nodeService.updateNode(node);
        }
    }

}
