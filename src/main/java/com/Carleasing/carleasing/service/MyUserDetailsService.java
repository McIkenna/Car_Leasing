package com.Carleasing.carleasing.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

      // User user = new User("user","password", new ArrayList<>());

        //User admin = new User("admin","adminPassword", new ArrayList<>());


       return new User("admin", "password", new ArrayList<>());
       // return new User(user.getUsername(), user.getPassword(), new ArrayList<>());

      /*  return new InMemoryResource(
                user,
                admin

        );*/
    }
}
