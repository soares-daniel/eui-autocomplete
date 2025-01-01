package com.sedam.eui.parser;

import com.sedam.eui.model.EuiComponent;
import com.sedam.eui.model.EuiComponentProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EuiDocsParser {

    public static List<EuiComponent> parseComponents(String docsPath) throws IOException {
        File docsDirectory = new File(docsPath + "/docs/components");
        List<EuiComponent> components = new ArrayList<>();

        if (docsDirectory.exists() && docsDirectory.isDirectory()) {
            File[] htmlFiles = docsDirectory.listFiles((dir, name) -> name.endsWith(".html"));

            if (htmlFiles != null) {
                for (File htmlFile : htmlFiles) {
                    EuiComponent component = parseComponentHtml(htmlFile);
                    if (component != null) {
                        components.add(component);
                    }
                }
            }
        }

        return components;
    }

    public static EuiComponent parseComponentHtml(File htmlFile) throws IOException {
        Document doc = Jsoup.parse(htmlFile, "UTF-8");

        // Extract component name
        Element breadcrumb = doc.selectFirst("li.breadcrumb-item:last-of-type");
        String componentName = breadcrumb != null ? breadcrumb.text() : "";

        // Extract selector
        Element selector = doc.selectFirst("td:contains(selector) + td code");
        String componentSelector = selector != null ? selector.text() : "";

        // Skip components without selector
        if (componentSelector.isEmpty()) {
            return null;
        }

        Element inputsSection = doc.selectFirst("section[data-compodoc=block-inputs]");
        List<EuiComponentProperty> componentInputs = new ArrayList<>();
        if (inputsSection != null) {
            Elements inputs = inputsSection.select("tbody");
            for (Element input : inputs) {
                Element nameElement = input.selectFirst("a[name] + b");
                Element typeElement = input.selectFirst("td:contains(Type :) code");
                EuiComponentProperty property = new EuiComponentProperty(
                    nameElement != null ? nameElement.text() : "",
                    typeElement != null ? typeElement.text() : ""
                );
                componentInputs.add(property);
            }
        }

        Element outputsSection = doc.selectFirst("section[data-compodoc=block-outputs]");
        List<EuiComponentProperty> componentOutputs = new ArrayList<>();
        if (outputsSection != null) {
            Elements outputs = outputsSection.select("tbody");
            for (Element output : outputs) {
                Element nameElement = output.selectFirst("a[name] + b");
                Element typeElement = output.selectFirst("td:contains(Type :) code");
                EuiComponentProperty property = new EuiComponentProperty(
                        nameElement != null ? nameElement.text() : "",
                        typeElement != null ? typeElement.text() : ""
                );
                componentOutputs.add(property);
            }
        }

        // Extract host directives
        Element hostDirectives = doc.selectFirst("td:contains(HostDirectives) + td div");
        Map<String, List<String>> hostDirectivesMap = new HashMap<>();
        if (hostDirectives != null) {
            String[] directiveParts = hostDirectives.text().split(":");
            String directiveCategory = "";
            for (int i = 0; i < directiveParts.length; i++) {
                if (i % 2 == 0) {
                    directiveCategory = directiveParts[i].trim();
                } else {
                    List<String> directiveNames = Arrays.stream(directiveParts[i].split("\\s+"))
                            .filter(name -> !name.isEmpty())
                            .toList();
                    hostDirectivesMap.put(directiveCategory, directiveNames);
                }
            }
        }

        return new EuiComponent(componentName, componentSelector, componentInputs, componentOutputs, hostDirectivesMap);
    }
}
