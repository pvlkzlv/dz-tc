package com.example.dztc.api;

import org.testng.annotations.BeforeSuite;

public class SetupTest extends BaseApiTest {
    @BeforeSuite
    public void setAuthSettings() {
        //TODO figure out the way to avoid blank strings
        var authSettings = checkedWithSuperUser.getAuthSettings().get("");
        authSettings.setPerProjectPermissions(true);
        checkedWithSuperUser.getAuthSettings().update(authSettings);
    }
}