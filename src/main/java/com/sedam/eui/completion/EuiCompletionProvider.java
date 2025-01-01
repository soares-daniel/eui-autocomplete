package com.sedam.eui.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import com.sedam.eui.registry.EuiTagRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EuiCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                    @NotNull ProcessingContext context,
                                    @NotNull CompletionResultSet result) {
        Set<String> euiTags = EuiTagRegistry.getRegistry().keySet();
        for (String tag : euiTags) {
            result.addElement(LookupElementBuilder.create(tag));
        }
    }
}
