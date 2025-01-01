package com.sedam.eui.descriptor;

import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlElementDescriptor;
import com.sedam.eui.registry.EuiTagRegistry;

import java.util.Set;

public class EuiTagDescriptorProvider implements XmlElementDescriptorProvider {

    @Override
    public XmlElementDescriptor getDescriptor(XmlTag tag) {
        if (isEuiTag(tag)) {
            return new EuiTagDescriptor(tag.getName());
        }
        return null; // Let the default descriptor provider handle this tag
    }

    private boolean isEuiTag(XmlTag tag) {
        Set<String> euiTags = EuiTagRegistry.getRegistry().keySet();
        return euiTags.contains(tag.getName());
    }
}

