package com.example.TaskManager.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServices implements UserDetailsService{

    @Autowired
    private UsersRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByEmpId(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        // System.out.println(username+" "+user);
        return new MyUserDetails(user);
    }
    
}
