package com.sedam.eui.model;

import java.util.List;
import java.util.Map;

public record EuiComponent (
        String name,
        String selector,
        List<EuiComponentProperty> inputs,
        List<EuiComponentProperty> outputs,
        Map<String, List<String>> hostDirectives
) { }