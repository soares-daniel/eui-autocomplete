package com.sedam.eui.registry;

import com.intellij.openapi.project.Project;
import com.sedam.eui.exception.EuiNotFoundException;
import com.sedam.eui.model.EuiComponent;
import com.sedam.eui.parser.EuiDocsParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EuiTagRegistry {

    private static final Logger log = LoggerFactory.getLogger(EuiTagRegistry.class);

    private static final Map<String, EuiComponent> registry = new HashMap<>();

    protected static void build(Project project) throws EuiNotFoundException, IOException {
        log.info("Building EUI registry");
        List<String> euiComponentPackages = EuiComponentResolver.getEuiComponentPackages(project);
        log.info("EUI component packages: {}", euiComponentPackages);
        List<EuiComponent> components = new ArrayList<>();
        for (String euiComponentPackage : euiComponentPackages) {
            components.addAll(EuiDocsParser.parseComponents(euiComponentPackage));
        }

        components.forEach(component -> registry.put(component.selector(), component));
    }

    public static Map<String, EuiComponent> getRegistry() {
        return registry;
    }

    protected static void clearRegistry() {
        registry.clear();
    }
}