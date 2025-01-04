package com.sedam.eui.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiDocumentManager;
import org.jetbrains.annotations.NotNull;

public class EuiInsertHandler implements InsertHandler<LookupElement> {
    private final String prefix; // The prefix to add (e.g., "[", "(", "[(")
    private final String suffix; // The suffix to add (e.g., "]", ")", ")]")

    public EuiInsertHandler(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement element) {
        Editor editor = context.getEditor();
        Document document = editor.getDocument();

        // Insert the prefix and suffix with `=""`
        int offset = context.getStartOffset();
        String toInsert = prefix + element.getLookupString() + suffix + "=\"\"";

        document.replaceString(offset, context.getTailOffset(), toInsert);

        // Commit the document to apply the changes
        PsiDocumentManager.getInstance(context.getProject()).commitDocument(document);

        // Move the caret inside the quotes
        editor.getCaretModel().moveToOffset(offset + toInsert.length() - 1);
    }
}

