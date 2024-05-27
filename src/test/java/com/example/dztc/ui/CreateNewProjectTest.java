package com.example.dztc.ui;

import com.codeborne.selenide.Condition;
import com.example.dztc.ui.pages.admin.CreateNewProject;
import com.example.dztc.ui.pages.favorites.ProjectsPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class CreateNewProjectTest extends BaseUiTest {

    @Test(groups = "UI")
    public void authorizedUserShouldBeAbleToCreateNewProject() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";
        loginAsUser(testData.getUser());
        new CreateNewProject()
            .open(testData.getProject().getParentProject().getLocator())
            .createProjectByUrl(url)
            .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new ProjectsPage()
            .open()
            .getSubProjects()
            .stream()
            .reduce((first, second) -> second)
            .get()
            .getHeader()
            .shouldHave(Condition.text(testData.getProject().getName()));

        checkedWithSuperUser.getProjectRequest().get(testData.getProject().getName());
    }

    @Test(groups = "UI")
    public void userShouldNotBeAbleToCreateNewProjectWithInvalidUrl() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-aq";
        loginAsUser(testData.getUser());
        new CreateNewProject()
            .open(testData.getProject().getParentProject().getLocator())
            .createProjectByUrl(url)
            .verifyInvalidUrlErrorMessageIsDisplayed();
        uncheckedWithSuperUser
            .getProjectRequest()
            .get(testData.getProject().getName())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(groups = "UI")
    public void projectShouldNotBeCreatedWithEmptyUrl() {
        var testData = testDataStorage.addTestData();
        loginAsUser(testData.getUser());
        new CreateNewProject()
            .open(testData.getProject().getParentProject().getLocator())
            .createProjectByUrl("")
            .verifyEmptyUrlErrorMessageIsDisplayed();
        uncheckedWithSuperUser
            .getProjectRequest()
            .get(testData.getProject().getName())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND);

    }

    @Test(groups = "UI")
    public void userShouldNotBeAbleToCreateNewProjectWithEmptyValues() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";
        loginAsUser(testData.getUser());
        new CreateNewProject()
            .open(testData.getProject().getParentProject().getLocator())
            .createProjectByUrl(url)
            .clearDefaultBranchInput()
            .setupProject("", "")
            .verifyEmptyProjectNameErrorIsDisplayed()
            .verifyEmptyBuildTypeNameErrorIsDisplayed()
            .verifyEmptyBranchNameErrorIsDisplayed();
    }
}