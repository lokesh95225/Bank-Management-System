package com.prismbyte.banking_app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
public class LoggingStartupVerifier {

    @Value("${logging.file.name:logs/banking-app.log}")
    private String logFileName;

    @EventListener(ApplicationReadyEvent.class)
    public void verifyLogFile() {
        Path logFile = Path.of(logFileName).toAbsolutePath().normalize();
        try {
            Files.createDirectories(logFile.getParent());
            boolean exists = Files.exists(logFile);
            log.info("Application log file configured at: {}", logFile);
            log.info("Application log file exists: {}", exists);
        } catch (IOException ex) {
            log.error("Unable to verify application log file path {}", logFile, ex);
        }
    }
}
