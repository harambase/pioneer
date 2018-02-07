package com.harambase.pioneer.security;

import com.harambase.pioneer.security.model.User;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.pojo.base.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by fan.jin on 2016-10-31.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PersonServer personServer;

    @Autowired
    public CustomUserDetailsService(PersonServer personServer) {
        this.personServer = personServer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Person user = (Person) personServer.get(username).getData();
        User userDetail = new User(user);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return userDetail;
        }
    }

}
