package com.example.dztc.ui.pages;

import static com.codeborne.selenide.Selenide.element;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.dztc.ui.Selectors;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class StartUpPage extends Page {

    private SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private SelenideElement acceptLicense = element(Selectors.byClass("rightLabel"));
    private SelenideElement header = element(Selectors.byId("header"));

    @Step("Open start up page")
    public StartUpPage open() {
        Selenide.open("/");
        return this;
    }

    @Step("Setup teamcity server")
    public StartUpPage setupTeamcityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        acceptLicense.shouldBe(Condition.enabled, Duration.ofMinutes(5));
        acceptLicense.scrollTo();
        acceptLicense.click();
        submitButton.click();
        return this;
    }
}
