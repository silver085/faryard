package com.faryard.api.configurators;

import com.faryard.api.domain.Role;
import com.faryard.api.domain.Roles;
import com.faryard.api.domain.User;
import com.faryard.api.repositories.RoleRepository;
import com.faryard.api.repositories.UsersRepository;
import com.faryard.api.services.impl.node.ActionService;
import com.faryard.api.services.impl.node.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class StartupApplication implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StartupApplication.class);
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application event: {}", event);


    }
}
