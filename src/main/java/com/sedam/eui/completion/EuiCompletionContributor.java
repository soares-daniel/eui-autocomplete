package com.sedam.eui.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTokenType;

public class EuiCompletionContributor extends CompletionContributor {
    
    public EuiCompletionContributor() {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(XmlTokenType.XML_NAME).withParent(XmlTag.class),
                new EuiMainComponentCompletionProvider()
        );
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(XmlTokenType.XML_NAME).withParent(XmlAttribute.class),
                new EuiInnerComponentCompletionProvider()
        );
    }
}
