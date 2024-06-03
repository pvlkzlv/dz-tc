package com.example.dztc.ui.pages.admin;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.element;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.dztc.ui.Selectors;
import com.example.dztc.ui.pages.Page;
import io.qameta.allure.Step;

public class CreateNewProject extends Page {
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement succesfullConnectionLabel = element(Selectors.byClass("connectionSuccessful"));
    private SelenideElement urlErrorLabel = element(Selectors.byId("error_url"));
    private SelenideElement projectNameErrorLabel = element(Selectors.byId("error_projectName"));
    private SelenideElement buildTypeNameErrorLabel = element(Selectors.byId("error_buildTypeName"));
    private SelenideElement defaultBranchInput = element(Selectors.byId("branch"));
    private SelenideElement defaultBranchErrorLabel = element(Selectors.byId("error_branch"));

    @Step
    public CreateNewProject open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    @Step
    public CreateNewProject createProjectByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    @Step
    public CreateNewProject setupProject(String projectName, String buildTypeName) {
        succesfullConnectionLabel.shouldBe(Condition.visible, Duration.ofSeconds(30));
        projectNameInput.shouldBe(Condition.interactable).clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
        return this;
    }

    @Step
    public CreateNewProject verifyInvalidUrlErrorMessageIsDisplayed() {
        urlErrorLabel
            .shouldBe(Condition.visible)
            .shouldBe(exactText(
                "git -c credential.helper= ls-remote origin command failed.\n" + "exit code: 128\n" + "stderr: " +
                "fatal:" + " could not read Username for 'https://github.com': No such " + "device or address"));
        return this;
    }

    @Step
    public CreateNewProject verifyEmptyUrlErrorMessageIsDisplayed() {
        urlErrorLabel.shouldBe(Condition.visible).shouldBe(exactText("URL must not be empty"));
        return this;
    }

    @Step
    public CreateNewProject verifyEmptyProjectNameErrorIsDisplayed() {
        projectNameErrorLabel.shouldBe(Condition.visible).shouldBe(exactText("Project name must not be empty"));
        return this;
    }

    @Step
    public CreateNewProject verifyEmptyBuildTypeNameErrorIsDisplayed() {
        buildTypeNameErrorLabel
            .shouldBe(Condition.visible)
            .shouldBe(exactText("Build configuration name must not be empty"));
        return this;
    }

    @Step
    public CreateNewProject clearDefaultBranchInput() {
        defaultBranchInput.shouldBe(Condition.interactable, Duration.ofSeconds(30)).clear();
        return this;
    }

    @Step
    public void verifyEmptyBranchNameErrorIsDisplayed() {
        defaultBranchErrorLabel.shouldBe(exactText("Branch name must be specified"));
    }
}