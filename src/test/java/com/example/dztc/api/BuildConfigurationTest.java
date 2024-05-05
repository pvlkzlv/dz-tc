package com.example.dztc.api;

import com.example.dztc.api.generators.RandomData;
import com.example.dztc.api.requests.CheckedRequests;
import com.example.dztc.api.requests.UncheckedRequests;
import com.example.dztc.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends SetupTest {

    @Test
    public void userCanAddNewBuildConfigurationToProject() {
        var testData = testDataStorage.addTestData();
        var testData2 = testDataStorage.addTestData();
        testData2.getBuildType().setProject(testData.getProject());
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
        var buildConfig = checkedRequests.getBuildConfigRequest().create(testData.getBuildType());
        var buildConfig2 = checkedRequests.getBuildConfigRequest().create(testData2.getBuildType());
        softy
            .assertThat(checkedRequests.getBuildConfigRequest().get(buildConfig.getId()).getId())
            .isEqualTo(testData.getBuildType().getId());
        softy
            .assertThat(checkedRequests.getBuildConfigRequest().get(buildConfig2.getId()).getId())
            .isEqualTo(testData2.getBuildType().getId());
    }

    @Test
    public void userCanCreateBuildConfigurationWithoutIdTest() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setId(null);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
        var buildConfig = checkedRequests.getBuildConfigRequest().create(testData.getBuildType());
        softy
            .assertThat(checkedRequests.getBuildConfigRequest().get(buildConfig.getId()).getId())
            .isEqualTo(testData.getProject().getId() + "_" + testData.getBuildType().getName());
    }

    @Test
    public void userCantCreateBuildConfigurationWithoutName() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setName(null);
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var uncheckedRequests = new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        uncheckedRequests.getProjectRequest().create(testData.getProject());
        uncheckedRequests
            .getBuildConfigRequest()
            .create(testData.getBuildType())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(Matchers.containsString("When creating a build type, non empty name should be provided."));
    }

    @Test
    public void userCantCreateBuildConfigurationWithEmptyStringAsName() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setName("");
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var uncheckedRequests = new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        uncheckedRequests.getProjectRequest().create(testData.getProject());
        uncheckedRequests
            .getBuildConfigRequest()
            .create(testData.getBuildType())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(Matchers.containsString("When creating a build type, non empty name should be provided."));
    }

    @Test
    public void userCanCreateBuildConfigurationWithSpecificCharactersInName() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setName(RandomData.getStringWithSpecialSymbols());
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
        var buildConfig = checkedRequests.getBuildConfigRequest().create(testData.getBuildType());
        softy
            .assertThat(checkedRequests.getBuildConfigRequest().get(buildConfig.getId()).getName())
            .isEqualTo(testData.getBuildType().getName());
    }

    @Test
    public void userCantCreateBuildConfigurationWithSpecificCharactersInId() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setId(RandomData.getStringWithSpecialSymbols());
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
            .getBuildConfigRequest()
            .create(testData.getBuildType())
            .then()
            .assertThat()
            .statusCode(org.apache.hc.core5.http.HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .body(Matchers.containsString(
                "ID should start with a latin letter and contain only latin letters, digits and " + "underscores (at "
                + "most " + "225 characters)."));
    }

    @Test
    public void userCanCreateBuildConfigurationWithIdInCharacterLimit() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setId(RandomData.getString(221));
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
        var buildConfig = checkedRequests.getBuildConfigRequest().create(testData.getBuildType());
        softy
            .assertThat(checkedRequests.getBuildConfigRequest().get(buildConfig.getId()).getName())
            .isEqualTo(testData.getBuildType().getName());
    }

    @Test
    public void userCanCreateBuildConfigurationWithIdOutsideOfCharacterLimit() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setId(RandomData.getString(222));
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
        new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
            .getBuildConfigRequest()
            .create(testData.getBuildType())
            .then()
            .assertThat()
            .statusCode(org.apache.hc.core5.http.HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .body(Matchers.containsString(
                "ID should start with a latin letter and contain only latin letters, digits and " + "underscores (at "
                + "most " + "225 characters)."));
    }

    @Test
    public void userCantAddBuildConfigurationToDeletedProject() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getUserRequest().create(testData.getUser());
        var checkedRequests = new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        var uncheckedRequests = new UncheckedRequests(Specifications.getSpec().authSpec(testData.getUser()));
        checkedRequests.getProjectRequest().create(testData.getProject());
        uncheckedRequests.getProjectRequest().delete(testData.getProject().getId());
        uncheckedRequests
            .getBuildConfigRequest()
            .create(testData.getBuildType())
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(Matchers.containsString(
                "No project found by locator" + " 'count:1,id:" + testData.getProject().getId() + "'"));
    }
}