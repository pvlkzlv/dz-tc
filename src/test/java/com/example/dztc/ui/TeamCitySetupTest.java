package com.example.dztc.ui;

import com.codeborne.selenide.Condition;
import com.example.dztc.ui.pages.StartUpPage;
import org.testng.annotations.Test;

public class TeamCitySetupTest extends BaseUiTest {

    @Test
    public void startUpTest() {
        new StartUpPage()
            .open()
            .setupTeamcityServer()
            .getHeader()
            .shouldHave(Condition.text("Create Administrator Account"));
    }
}