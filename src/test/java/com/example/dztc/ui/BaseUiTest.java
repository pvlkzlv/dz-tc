package com.example.dztc.ui;

import com.codeborne.selenide.Configuration;
import com.example.dztc.api.BaseTest;
import com.example.dztc.api.config.Config;
import com.example.dztc.api.models.User;
import com.example.dztc.api.requests.CheckedRequests;
import com.example.dztc.api.spec.Specifications;
import com.example.dztc.ui.pages.LoginPage;
import org.testng.annotations.BeforeSuite;

public class BaseUiTest extends BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setupUiTests() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder = "target/downloads";
        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getUserRequest().create(user);
        new LoginPage().login(user);
    }
}