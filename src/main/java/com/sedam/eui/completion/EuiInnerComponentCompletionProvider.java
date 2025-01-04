package com.sedam.eui.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.html.HtmlTagImpl;
import com.intellij.util.ProcessingContext;
import com.sedam.eui.model.EuiComponent;
import com.sedam.eui.model.EuiComponentProperty;
import com.sedam.eui.registry.EuiTagRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public class EuiInnerComponentCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();
        String mainTagName = "";
        try {
            mainTagName = ((HtmlTagImpl) position.getParent().getParent()).getName();
        } catch (ClassCastException e) {
            // Ignore
        }

        if (mainTagName.isEmpty()) {
            return;
        }

        // Handle inner components (e.g., button[euiButton])
        Map<String, EuiComponent> innerComponents = EuiTagRegistry.getInnerComponents();
        for (Map.Entry<String, EuiComponent> entry : innerComponents.entrySet()) {
            String selector = entry.getKey();
            EuiComponent component = entry.getValue();

            if (matchesSelector(selector, mainTagName) || isGlobalSelector(selector)) {
                String strippedTag = selector.substring(selector.indexOf('[') + 1, selector.indexOf(']'));

                HtmlTagImpl currentTag = (HtmlTagImpl) position.getParent().getParent();
                if (currentTag.getAttribute(strippedTag) != null) {
                    component.inputs().forEach(input -> createInsertHandler(result).apply(input));
                    component.outputs().forEach(output -> createOutputInsertHandler(result).apply(output));
                } else {
                    result.addElement(LookupElementBuilder.create(strippedTag));
                }
            }
        }

        // Handle main components (e.g., button)
        Map<String, EuiComponent> components = EuiTagRegistry.getMainComponents();
        EuiComponent component = components.get(mainTagName);
        if (component != null) {
            component.inputs().forEach(input -> createInsertHandler(result).apply(input));
            component.outputs().forEach(output -> createOutputInsertHandler(result).apply(output));
        }
    }

    /**
     * Checks if the given selector matches the current XML tag name.
     *
     * @param selector      The full selector string (e.g., "button[euiButton]").
     * @param currentTagName The name of the current tag (e.g., "button").
     * @return True if the selector applies to the current tag, false otherwise.
     */
    private boolean matchesSelector(String selector, String currentTagName) {
        if (selector.contains("[")) {
            // Complex selector: button[euiButton]
            String tagPart = selector.substring(0, selector.indexOf('[')).trim();
            return tagPart.equals(currentTagName);
        }
        // Simple selector: button
        return selector.equals(currentTagName);
    }

    private boolean isGlobalSelector(String selector) {
        return selector.startsWith("[") && selector.endsWith("]");
    }

    private static Function<EuiComponentProperty, Void> createInsertHandler(CompletionResultSet result) {
        return property -> {
            String suffix = property.type().equals("string") ? "" : "]";
            result.addElement(
                    LookupElementBuilder.create(property.name())
                            .withInsertHandler(new EuiInsertHandler(suffix.equals("]") ? "[" : "", suffix))
            );
            return null;
        };
    }

    private static Function<EuiComponentProperty, Void> createOutputInsertHandler(CompletionResultSet result) {
        return property -> {
            result.addElement(
                    LookupElementBuilder.create(property.name())
                            .withInsertHandler(new EuiInsertHandler("(", ")"))
            );
            return null;
        };
    }
}
