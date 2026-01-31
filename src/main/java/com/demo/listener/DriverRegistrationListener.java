package com.demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class DriverRegistrationListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(DriverRegistrationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String driverClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverClass);
            log.info("[DB-Listener] Successfully loaded and registered JDBC driver: " + driverClass);
        } catch (ClassNotFoundException e) {
            log.error("[DB-Listener] FATAL: JDBC Driver not found: " + driverClass + ". Ensure the MySQL connector JAR is in WEB-INF/lib.", e);
            // This is a critical failure, the application cannot function without a DB driver.
            throw new RuntimeException("Failed to register JDBC driver", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Deregistration logic can go here if needed, but is often handled by the container.
        log.info("[DB-Listener] Context destroyed. JDBC driver will be deregistered by the container.");
    }
}
