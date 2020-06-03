package com.java.web.listener;

import com.java.web.auth.registry.AuthMetadataRegistry;
import com.java.web.auth.registry.AuthStateRegistry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AuthMetadataRegistry.getInstance();
        AuthStateRegistry.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
