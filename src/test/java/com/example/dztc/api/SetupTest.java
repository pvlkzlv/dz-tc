package com.example.dztc.api;

import com.example.dztc.api.models.AuthorizedInfoModel;
import org.testng.annotations.Test;

public class SetupTest extends BaseApiTest {

    @Test
    public void setAuthSettings() {
        var authSettings = checkedWithSuperUser.getAuthSettings().get("");
        authSettings.setPerProjectPermissions(true);
        checkedWithSuperUser.getAuthSettings().update(authSettings);
    }

    @Test
    public void authorizeAgent() {
        var agent = checkedWithSuperUser.getCheckedAgents().get().getAgent().get(0);
        var infoModel = AuthorizedInfoModel.builder().status(true).build();
        checkedWithSuperUser.getCheckedAgents().put(agent, infoModel);
    }
}