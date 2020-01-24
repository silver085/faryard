package com.kaufeundverkaufe.api.services.impl;


import com.kaufeundverkaufe.api.DTO.UserRegistration;
import com.kaufeundverkaufe.api.domain.Role;
import com.kaufeundverkaufe.api.domain.Roles;
import com.kaufeundverkaufe.api.domain.User;
import com.kaufeundverkaufe.api.repositories.RoleRepository;
import com.kaufeundverkaufe.api.repositories.UsersRepository;
import com.kaufeundverkaufe.api.services.impl.exceptions.UserAlreadyExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MongoUserService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncrypterService passwordEncrypterService;

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
}
