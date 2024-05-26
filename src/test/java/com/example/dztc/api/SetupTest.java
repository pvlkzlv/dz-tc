package com.example.dztc.api;

import org.testng.annotations.BeforeSuite;

public class SetupTest extends BaseApiTest {

    @BeforeSuite(alwaysRun = true)
    public void setAuthSettings() {
        System.out.println("Setting up auth settings and debug");
        var authSettings = checkedWithSuperUser.getAuthSettings().get("");
        authSettings.setPerProjectPermissions(true);
        checkedWithSuperUser.getAuthSettings().update(authSettings);
    }
}