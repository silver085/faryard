package com.faryard.api.configurators;

import com.faryard.api.services.impl.node.ActionService;
import com.faryard.api.services.impl.node.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupApplication implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StartupApplication.class);

    @Autowired
    ActionService actionService;
    @Autowired
    NodeService nodeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application event: {}", event);


    }
}
