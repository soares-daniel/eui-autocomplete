package com.sedam.eui.descriptor;

import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlElementsGroup;
import com.intellij.xml.XmlNSDescriptor;
import com.sedam.eui.registry.EuiTagRegistry;
import com.sedam.eui.model.EuiComponent;
import com.sedam.eui.model.EuiComponentProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class EuiTagDescriptor implements XmlElementDescriptor {

    private final String tagName;

    public EuiTagDescriptor(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String getQualifiedName() {
        return tagName;
    }

    @Override
    public String getDefaultName() {
        return tagName;
    }

    @Override
    public XmlElementDescriptor[] getElementsDescriptors(XmlTag context) {
        return new XmlElementDescriptor[0];
    }

    @Nullable
    @Override
    public XmlElementDescriptor getElementDescriptor(XmlTag childTag, XmlTag contextTag) {
        // EUI tags typically don't have custom children, so return null
        return null;
    }

    @Override
    public XmlAttributeDescriptor[] getAttributesDescriptors(@Nullable XmlTag context) {
        EuiComponent component = EuiTagRegistry.getRegistry().get(tagName);
        if (component != null) {
            List<EuiComponentProperty> inputs = component.inputs();
            List<EuiComponentProperty> outputs = component.outputs();

            // Convert inputs and outputs to XmlAttributeDescriptors
            List<XmlAttributeDescriptor> descriptors = inputs.stream()
                    .map(input -> new EuiAttributeDescriptor("[" + input.name() + "]", input.type()))
                    .collect(Collectors.toList());

            descriptors.addAll(outputs.stream()
                    .map(output -> new EuiAttributeDescriptor("(" + output.name() + ")", output.type()))
                    .toList());

            return descriptors.toArray(new XmlAttributeDescriptor[0]);
        }
        return new XmlAttributeDescriptor[0];
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(@NotNull String attributeName, @Nullable XmlTag context) {
        // Return a specific attribute descriptor for a given attribute name
        EuiComponent component = EuiTagRegistry.getRegistry().get(tagName);
        if (component != null) {
            for (EuiComponentProperty input : component.inputs()) {
                if (("[" + input.name() + "]").equals(attributeName)) {
                    return new EuiAttributeDescriptor(attributeName, input.type());
                }
            }
            for (EuiComponentProperty output : component.outputs()) {
                if (("(" + output.name() + ")").equals(attributeName)) {
                    return new EuiAttributeDescriptor(attributeName, output.type());
                }
            }
        }
        return null;
    }

    @Override
    public @Nullable XmlAttributeDescriptor getAttributeDescriptor(XmlAttribute attribute) {
        return null;
    }

    @Nullable
    @Override
    public PsiElement getDeclaration() {
        // Optionally return the declaration of this tag (e.g., in the EUI library source)
        return null;
    }

    @Override
    public String getName(PsiElement context) {
        return tagName;
    }

    @Override
    public @NlsSafe String getName() {
        return "";
    }

    @Override
    public void init(PsiElement element) {
        // Initialization logic, if needed
    }

    @Override
    public Object @NotNull [] getDependencies() {
        // Return dependencies, if needed
        return new Object[0];
    }

    @Nullable
    @Override
    public XmlNSDescriptor getNSDescriptor() {
        return null; // Namespace descriptor, if applicable
    }

    @Override
    public @Nullable XmlElementsGroup getTopGroup() {
        return null;
    }

    @Override
    public int getContentType() {
        // Defines what content the tag can contain; return CONTENT_TYPE_ANY for flexibility
        return CONTENT_TYPE_ANY;
    }

    @Override
    public @Nullable String getDefaultValue() {
        return "";
    }
}
