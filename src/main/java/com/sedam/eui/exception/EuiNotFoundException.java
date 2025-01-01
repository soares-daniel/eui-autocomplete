package com.sedam.eui.exception;

public class EuiNotFoundException extends Exception {
    public EuiNotFoundException() {
        super("EUI components not found");
    }
}
