package com.example.dztc.api;

import org.testng.annotations.Test;

public class SetupTest extends BaseApiTest {

    @Test
    public void setAuthSettings() {
        var authSettings = checkedWithSuperUser.getAuthSettings().get("");
        authSettings.setPerProjectPermissions(true);
        checkedWithSuperUser.getAuthSettings().update(authSettings);
    }
}