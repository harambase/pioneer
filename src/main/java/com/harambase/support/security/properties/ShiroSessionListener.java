package com.harambase.support.security.properties;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
        logger.info("Session Increment, current active session: "+sessionCount.get());
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        logger.info("Session decrement, current active session = "+sessionCount.get());
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        logger.info("Session expire, current active session"+sessionCount.get());

    }
}