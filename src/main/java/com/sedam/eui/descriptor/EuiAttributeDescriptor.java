package com.sedam.eui.descriptor;

import com.intellij.openapi.util.NlsContexts;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlElement;
import com.intellij.xml.XmlAttributeDescriptor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

public class EuiAttributeDescriptor implements XmlAttributeDescriptor {

    private final String name;
    private final String type;

    public EuiAttributeDescriptor(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PsiElement getDeclaration() {
        return null;
    }

    @Override
    public @NonNls String getName(PsiElement context) {
        return "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void init(PsiElement element) {

    }

    @Override
    public boolean isRequired() {
        return false; // Set true if the attribute is required
    }

    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public boolean hasIdType() {
        return false;
    }

    @Override
    public boolean hasIdRefType() {
        return false;
    }

    @Nullable
    @Override
    public String getDefaultValue() {
        return null; // Provide a default value if applicable
    }

    @Override
    public boolean isEnumerated() {
        return false;
    }

    @Override
    public String @Nullable [] getEnumeratedValues() {
        return new String[0];
    }

    @Override
    public @Nullable @NlsContexts.DetailedDescription String validateValue(XmlElement context, String value) {
        return "";
    }
}
