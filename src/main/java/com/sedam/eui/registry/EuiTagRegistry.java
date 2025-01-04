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

        // Process components and normalize selectors
        for (EuiComponent component : components) {
            String selector = component.selector();
            if (selector.contains(",")) {
                // Split multiple selectors
                String[] selectors = selector.split(",");
                for (String singleSelector : selectors) {
                    addComponentToRegistry(singleSelector.trim(), component);
                }
            } else {
                addComponentToRegistry(selector.trim(), component);
            }
        }
    }

    private static void addComponentToRegistry(String selector, EuiComponent component) {
        // For global selectors like [exampleSelector], apply to all tags
        if (selector.startsWith("[") && selector.endsWith("]")) {
            registry.put(selector, component);
        } else {
            registry.put(selector, component);
        }
    }

    public static Map<String, EuiComponent> getRegistry() {
        return registry;
    }

    public static Map<String, EuiComponent> getMainComponents() {
        Map<String, EuiComponent> mainComponents = new HashMap<>();
        for (String selector : registry.keySet()) {
            if (!selector.contains("[") || selector.startsWith("[") && selector.endsWith("]")) {
                mainComponents.put(selector, registry.get(selector));
            }
        }
        return mainComponents;
    }

    public static Map<String, EuiComponent> getInnerComponents() {
        Map<String, EuiComponent> innerComponents = new HashMap<>();
        for (String selector : registry.keySet()) {
            if (selector.contains("[") && !selector.startsWith("[")) {
                innerComponents.put(selector, registry.get(selector));
            }
        }
        return innerComponents;
    }

    protected static void clearRegistry() {
        registry.clear();
    }
}