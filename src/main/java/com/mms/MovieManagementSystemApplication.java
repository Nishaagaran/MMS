package com.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@SpringBootApplication
public class MovieManagementSystemApplication {

    public static void main(String[] args) {
        // #region agent log
        try {
            String logPath = "c:\\MMS\\.cursor\\debug.log";
            String logEntry = String.format("{\"id\":\"log_%d\",\"timestamp\":%d,\"location\":\"MovieManagementSystemApplication.java:15\",\"message\":\"Application startup - checking dialect configuration\",\"data\":{\"hypothesisId\":\"A\",\"systemProperty\":\"%s\"},\"sessionId\":\"debug-session\",\"runId\":\"run1\"}\n",
                System.currentTimeMillis(), System.currentTimeMillis(), System.getProperty("spring.jpa.database-platform"));
            Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {}
        // #endregion
        
        // #region agent log
        try {
            String logPath = "c:\\MMS\\.cursor\\debug.log";
            String h2ClassCheck = "";
            try {
                Class.forName("org.h2.Driver");
                h2ClassCheck = "found";
            } catch (ClassNotFoundException e) {
                h2ClassCheck = "not found";
            }
            String logEntry = String.format("{\"id\":\"log_%d\",\"timestamp\":%d,\"location\":\"MovieManagementSystemApplication.java:25\",\"message\":\"H2 driver class check\",\"data\":{\"hypothesisId\":\"B\",\"h2DriverStatus\":\"%s\"},\"sessionId\":\"debug-session\",\"runId\":\"run1\"}\n",
                System.currentTimeMillis(), System.currentTimeMillis(), h2ClassCheck);
            Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {}
        // #endregion
        
        // #region agent log
        try {
            String logPath = "c:\\MMS\\.cursor\\debug.log";
            String dialectClassCheck = "";
            try {
                Class.forName("org.hibernate.dialect.Dialect");
                dialectClassCheck = "abstract class found";
            } catch (ClassNotFoundException e) {
                dialectClassCheck = "not found: " + e.getMessage();
            }
            String logEntry = String.format("{\"id\":\"log_%d\",\"timestamp\":%d,\"location\":\"MovieManagementSystemApplication.java:35\",\"message\":\"Dialect abstract class check\",\"data\":{\"hypothesisId\":\"A\",\"dialectClassStatus\":\"%s\"},\"sessionId\":\"debug-session\",\"runId\":\"run1\"}\n",
                System.currentTimeMillis(), System.currentTimeMillis(), dialectClassCheck);
            Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {}
        // #endregion
        
        // #region agent log
        try {
            String logPath = "c:\\MMS\\.cursor\\debug.log";
            String h2DialectClassCheck = "";
            try {
                Class.forName("org.hibernate.dialect.H2Dialect");
                h2DialectClassCheck = "found";
            } catch (ClassNotFoundException e) {
                h2DialectClassCheck = "not found: " + e.getMessage();
            }
            String logEntry = String.format("{\"id\":\"log_%d\",\"timestamp\":%d,\"location\":\"MovieManagementSystemApplication.java:45\",\"message\":\"H2Dialect concrete class check\",\"data\":{\"hypothesisId\":\"A\",\"h2DialectClassStatus\":\"%s\"},\"sessionId\":\"debug-session\",\"runId\":\"run1\"}\n",
                System.currentTimeMillis(), System.currentTimeMillis(), h2DialectClassCheck);
            Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {}
        // #endregion
        
        try {
            SpringApplication.run(MovieManagementSystemApplication.class, args);
            // #region agent log
            try {
                String logPath = "c:\\MMS\\.cursor\\debug.log";
                String logEntry = String.format("{\"id\":\"log_%d\",\"timestamp\":%d,\"location\":\"MovieManagementSystemApplication.java:55\",\"message\":\"Application started successfully\",\"data\":{\"hypothesisId\":\"ALL\",\"status\":\"success\"},\"sessionId\":\"debug-session\",\"runId\":\"run1\"}\n",
                    System.currentTimeMillis(), System.currentTimeMillis());
                Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (Exception e) {}
            // #endregion
        } catch (Exception e) {
            // #region agent log
            try {
                String logPath = "c:\\MMS\\.cursor\\debug.log";
                String logEntry = String.format("{\"id\":\"log_%d\",\"timestamp\":%d,\"location\":\"MovieManagementSystemApplication.java:62\",\"message\":\"Application startup failed\",\"data\":{\"hypothesisId\":\"ALL\",\"errorType\":\"%s\",\"errorMessage\":\"%s\"},\"sessionId\":\"debug-session\",\"runId\":\"run1\"}\n",
                    System.currentTimeMillis(), System.currentTimeMillis(), e.getClass().getName(), e.getMessage().replace("\"", "'"));
                Files.write(Paths.get(logPath), logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (Exception ex) {}
            // #endregion
            throw e;
        }
    }
}

