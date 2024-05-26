package com.example.dztc.api;

import org.testng.annotations.Test;

public class SetupTest extends BaseApiTest {

    @Test
    public void setAuthSettings() {
        var agents = checkedWithSuperUser.getCheckedAgents().get();

        if (!agents.getAgent().isEmpty()) {
            checkedWithSuperUser.getCheckedAgents().put(agents.getAgent().get(0).getId(), "true");
        }
        else {
            System.out.println("Teamcity Agent not authorized");
        }
    }
}