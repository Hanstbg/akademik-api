package org.delcom.app.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupInfoLogger {

    private static final Logger logger = LoggerFactory.getLogger(StartupInfoLogger.class);

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("========================================");
        logger.info("  Akademik API sudah berjalan!");
        logger.info("  Base URL  : http://localhost:8080/api");
        logger.info("  Register  : POST /api/auth/register");
        logger.info("  Login     : POST /api/auth/login");
        logger.info("  Role      : DOSEN | MAHASISWA");
        logger.info("========================================");
    }
}
