package com.example.dztc.ui.pages.admin;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.element;
import static java.time.Duration.ofSeconds;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import com.example.dztc.ui.Selectors;
import com.example.dztc.ui.pages.Page;

public class CreateBuildConfigurationPage extends Page {
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement urlErrorLabel = element(Selectors.byId("error_url"));
    private SelenideElement buildConfigurationNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement buildTypeNameErrorLabel = element(Selectors.byId("error_buildTypeName"));
    private SelenideElement defaultBranchInput = element(Selectors.byId("branch"));
    private SelenideElement defaultBranchErrorLabel = element(Selectors.byId("error_branch"));
    private final SelenideElement manuallyOption = element(new ByAttribute("href", "#createManually"));

    public CreateBuildConfigurationPage open(String projectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId="
                      + projectId + "&showMode=createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateBuildConfigurationPage setUrl(String url) {
        urlInput.shouldBe(Condition.interactable, ofSeconds(30));
        urlInput.clear();
        urlInput.sendKeys(url);
        return this;
    }

    public CreateBuildConfigurationPage verifyEmptyUrlErrorMessageIsDisplayed() {
        urlErrorLabel.shouldBe(Condition.visible).shouldBe(exactText("URL must not be empty"));
        return this;
    }

    public CreateBuildConfigurationPage verifyEmptyBuildTypeNameErrorIsDisplayed() {
        buildTypeNameErrorLabel
            .shouldBe(Condition.visible)
            .shouldBe(exactText("Build configuration name must not be empty"));
        return this;
    }

    public void setupBuildConfiguration(String buildConfigurationName) {
        buildConfigurationNameInput.shouldBe(interactable, ofSeconds(30)).clear();
        buildConfigurationNameInput.sendKeys(buildConfigurationName);
        submit();
    }

    public CreateBuildConfigurationPage clickOnManuallyButton() {
        manuallyOption.shouldBe(interactable, ofSeconds(20)).click();
        return this;
    }
}
