package com.harambase.pioneer.security.service;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.security.JwtUserFactory;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.service.PersonService;
import com.harambase.support.util.ReturnMsgUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final PersonServer personServer;

    @Autowired
    public JwtUserDetailsServiceImpl(PersonServer personServer){
        this.personServer = personServer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            HaramMessage message = personServer.get(IP, PORT, username);
            if (message.getData() == null) {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            } else {
                Person person = new Person();
                BeanUtils.populate(person, (LinkedHashMap) message.getData());
                return JwtUserFactory.create(person);
            }


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
