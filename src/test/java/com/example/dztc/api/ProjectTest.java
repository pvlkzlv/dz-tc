package com.example.dztc.api;

import com.example.dztc.api.generators.RandomData;
import com.example.dztc.api.requests.CheckedRequests;
import com.example.dztc.api.requests.UncheckedRequests;
import com.example.dztc.api.requests.checked.CheckedProject;
import com.example.dztc.api.requests.checked.CheckedUser;
import com.example.dztc.api.requests.unchecked.UncheckedProject;
import com.example.dztc.api.spec.Specifications;
import org.apache.hc.core5.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProjectTest extends SetupTest {

    @Test
    public void userCanCreateProjectTest() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser())).create(
            testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void userCanCreateProjectOnlyWithIDAndName() {
        var testData = testDataStorage.addTestData();
        testData.getProject().setParentProject(null);
        testData.getProject().setCopyAllAssociatedSettings(null);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        var project = checkedRequests.getProjectRequest().create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void userCantCreateProjectWithoutName() {
        var testData = testDataStorage.addTestData();
        testData.getProject().setName(null);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
            .create(testData.getProject())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_CLIENT_ERROR)
            .body(Matchers.containsString("Project name cannot be empty."));
    }

    @Test()
    public void userCanCreateProjectWithSpecialCharacterInName() {
        var testData = testDataStorage.addTestData();
        var projectName = RandomData.getStringWithSpecialSymbols();
        testData.getProject().setName(projectName);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        var project = checkedRequests.getProjectRequest().create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
        softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
    }

    @Test
    public void userCantCreateProjectWithSpecialCharacterInID() {
        var testData = testDataStorage.addTestData();
        var projectName = RandomData.getStringWithSpecialSymbols();
        testData.getProject().setId(projectName);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var uncheckedRequests = new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        uncheckedRequests
            .getProjectRequest()
            .create(testData.getProject())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .body(Matchers.containsString(
                "ID should start with a latin letter and contain only latin letters, digits and underscores (at most "
                + "225 characters)."));
    }

    @Test
    public void userCanCreateProjectWithinCharactersLimitTest() {
        var testData = testDataStorage.addTestData();
        var projectName = RandomData.getString(221);
        testData.getProject().setName(projectName);
        testData.getProject().setId(projectName);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
    }

    @Test
    public void userCantCreateProjectOutOfCharactersLimitTest() {
        var testData = testDataStorage.addTestData();
        var projectName = RandomData.getString(222);
        testData.getProject().setName(projectName);
        testData.getProject().setId(projectName);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var uncheckedRequests = new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        uncheckedRequests
            .getProjectRequest()
            .create(testData.getProject())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .body(Matchers.containsString(
                "ID should start with a latin letter and contain only latin letters, digits and underscores (at most "
                + "225 characters)."));
    }

    @Test
    public void userCantCreateProjectWithSameNameTest() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser())).create(
            testData.getProject());
        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
            .create(testData.getProject())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(Matchers.containsString("Project with this name already exists: " + project.getName()));

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @DataProvider(name = "invalidWhitespaces")
    public Object[][] invalidWhitespaces() {
        return new Object[][] {{" "}, {"    "}};
    }

    @Test(dataProvider = "invalidWhitespaces")
    public void userCantCreateProjectWithWhitespacesAsNameTest(String projectName) {
        var testData = testDataStorage.addTestData();
        testData.getProject().setName(projectName);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
            .create(testData.getProject())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_SERVER_ERROR)
            .body(Matchers.containsString("Given project name is empty."));
    }

    @Test
    public void userCantCreateProjectWithoutNameTest() {
        var testData = testDataStorage.addTestData();
        testData.getProject().setName("");
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
            .create(testData.getProject())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_CLIENT_ERROR)
            .body(Matchers.containsString("Project name cannot be empty."));
    }

    @Test
    public void userCanCreateProjectWithParentProject() {
        var testData = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        var project = checkedRequests.getProjectRequest().create(testData.getProject());
        testData2.getProject().setParentProject(project);
        checkedRequests.getProjectRequest().create(testData2.getProject());
    }

    @Test
    public void userCanCreateProjectWithParentProjectAndNotCopyAssociatedSettings() {
        var testData = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        var project = checkedRequests.getProjectRequest().create(testData.getProject());
        testData2.getProject().setParentProject(project);
        testData2.getProject().setCopyAllAssociatedSettings(false);
        checkedRequests.getProjectRequest().create(testData2.getProject());
    }

    @Test
    public void userCantCreateProjectWithDeletedParentProject() {
        var testData = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        var uncheckedRequests = new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));

        var project = checkedRequests.getProjectRequest().create(testData.getProject());
        checkedWithSuperUser.getProjectRequest().delete(project.getId());
        testData2.getProject().setParentProject(project);
        uncheckedRequests
            .getProjectRequest()
            .create(testData2.getProject())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(Matchers.containsString(
                "No project found by locator 'count:1,id:" + project.getId() + "'. Project cannot be found by " +
                "external id '" + project.getId() + "'."));
    }
}