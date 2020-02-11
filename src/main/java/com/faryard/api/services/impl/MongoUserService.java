package com.faryard.api.services.impl;


import com.faryard.api.DTO.UserNodeDTO;
import com.faryard.api.DTO.UserProfileDTO;
import com.faryard.api.DTO.UserRegistration;
import com.faryard.api.domain.Role;
import com.faryard.api.domain.Roles;
import com.faryard.api.domain.User;
import com.faryard.api.domain.node.Node;
import com.faryard.api.domain.node.NodeStatus;
import com.faryard.api.services.impl.exceptions.UserAlreadyExistingException;
import com.faryard.api.repositories.RoleRepository;
import com.faryard.api.repositories.UsersRepository;
import com.faryard.api.services.impl.node.NodeService;
import com.faryard.api.utils.Mappers;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MongoUserService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncrypterService passwordEncrypterService;
    @Autowired
    private NodeService nodeService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(email);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = this.getUserAuthority(user.get().getRoles());
        return this.buildUserForAuthentication(user.get(), authorities);
        
    }

    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        return new ArrayList<>(roles);
    }


    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncrypterService.encodePassword(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole(Roles.USER.getRole());
        user.setRoles(Collections.singletonList(userRole));
        usersRepository.save(user);
    }

    public boolean userExist(String email){
        return usersRepository.findByEmail(email).isPresent();
    }

    public void registerNewUser(UserRegistration userRequest) throws UserAlreadyExistingException {
        if(userExist(userRequest.getEmail()))
            throw new UserAlreadyExistingException();


    }
    private User getSessionUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return springUserToAppUser((org.springframework.security.core.userdetails.User) auth.getPrincipal());
    }

    private User springUserToAppUser(org.springframework.security.core.userdetails.User user){
        String email = user.getUsername();
        return usersRepository.findByEmail(email).orElse(null);

    }
    private boolean isAdmin(User user){
        for(Role role : user.getRoles()){
            if(role.getRole().equals(Roles.ADMIN.getRole()))
                return true;
        }
        return false;
    }

    public UserProfileDTO getLoggedUser() {
        User loggedUser = getSessionUser();
        UserProfileDTO profile = new UserProfileDTO();
        profile.setEmail(loggedUser.getEmail());
        profile.setRoles(
                loggedUser
                        .getRoles()
                        .stream()
                        .map(Role::getRole)
                        .collect(Collectors.toList())
        );
        profile.setUserId(loggedUser.getId());
        profile.setUsername(loggedUser.getEmail());
        profile.setUserPicture("/static/img/admin.png");
        List<UserNodeDTO> myNodes = getMyNodes();
        profile.setMyNodes(myNodes);
        return profile;
    }

    public List<UserNodeDTO> getMyNodes() {
        List<Node> myNodes ;
        User loggedUser = getSessionUser();
        if(isAdmin(loggedUser)){
            myNodes = nodeService.getAllNodes();
        } else {
            //in caso di nodi associati
            myNodes = nodeService.getMyNodes(loggedUser.getId());
        }
        return Mappers.domainNodeToUserNodeDTO(myNodes);
    }

    public boolean isUserAllowedForNodeId(String nodeId) {
        User loggedUser = getSessionUser();
        if(isAdmin(loggedUser)) return true;
        List<Node> myNodes = nodeService.getMyNodes(loggedUser.getId());
        return myNodes.stream().anyMatch(n -> n.getId().equals(nodeId));
    }
}
