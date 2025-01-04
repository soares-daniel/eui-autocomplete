package com.sedam.eui.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import com.sedam.eui.model.EuiComponent;
import com.sedam.eui.registry.EuiTagRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class EuiMainComponentCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet result) {
        Map<String, EuiComponent> mainComponents = EuiTagRegistry.getMainComponents();
        Set<String> mainTags = mainComponents.keySet();
        for (String mainTag : mainTags) {
            result.addElement(LookupElementBuilder.create(mainTag));
        }
    }
}
