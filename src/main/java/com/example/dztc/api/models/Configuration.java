package com.example.dztc.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {
    private boolean allowGuest;
    private String guestUsername;
    private String welcomeText;
    private boolean collapseLoginForm;
    private boolean perProjectPermissions;
    private boolean emailVerification;
    private Modules modules;
}