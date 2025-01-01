package com.sedam.eui.registry;

import com.intellij.openapi.project.Project;
import com.sedam.eui.exception.EuiNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EuiRegistryManager {

    private static final Logger log = LoggerFactory.getLogger(EuiRegistryManager.class);

    private static boolean isInitialized = false;

    public static synchronized void initialize(Project project) throws EuiNotFoundException, IOException {
        log.info("Initializing EUI registry");
        if (!isInitialized) {
            EuiTagRegistry.clearRegistry();
            EuiTagRegistry.build(project);
            isInitialized = true;
        }
        log.info("EUI registry initialized");
    }

    public static synchronized void rebuild(Project project) throws EuiNotFoundException, IOException {
        log.info("Rebuilding EUI registry");
        EuiTagRegistry.clearRegistry();
        EuiTagRegistry.build(project);
        log.info("EUI registry rebuilt");
    }
}