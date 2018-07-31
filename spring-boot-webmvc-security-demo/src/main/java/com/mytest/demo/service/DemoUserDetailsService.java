package com.mytest.demo.service;

import com.mytest.demo.entity.DemoUser;
import com.mytest.demo.repository.DemoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DemoUserDetailsService implements UserDetailsService {

    @Autowired
    private DemoUserRepository demoUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DemoUser user = demoUserRepository.findOneByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
        }

        return user;
    }
}