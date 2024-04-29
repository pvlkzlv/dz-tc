package com.example.dztc.api.enums;

public enum Role {
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    PROJECT_ADMIN("PROJECT_ADMIN"),
    PROJECT_DEVELOPER("PROJECT_DEVELOPER"),
    PROJECT_VIEWER("PROJECT_VIEWER"),
    AGENT_MANAGER("AGENT_MANAGER");

    public String getText() {
        return text;
    }

    private String text;

    Role(String text) {
        this.text = text;
    }
}