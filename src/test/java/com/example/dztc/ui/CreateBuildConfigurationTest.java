package com.example.dztc.ui;

import static java.lang.String.format;

import com.example.dztc.api.requests.CheckedRequests;
import com.example.dztc.api.spec.Specifications;
import com.example.dztc.ui.pages.admin.CreateBuildConfigurationPage;
import org.testng.annotations.Test;

public class CreateBuildConfigurationTest extends BaseUiTest {

    private String getBuildConfigId(String projectId, String buildName) {
        String configId = buildName.replace("_", "").replaceFirst("t", "T");
        return format("%s_%s", projectId, configId);
    }

    @Test(groups = "UI")
    public void authorizedUserToCreateBuildConfiguration() {
        var testData = testDataStorage.addTestData();
        loginAsUser(testData.getUser());
        var url = "https://github.com/AlexPshe/spring-core-for-qa";
        new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
            .getProjectRequest()
            .create(testData.getProject());
        var createBuildConfigurationPage = new CreateBuildConfigurationPage();
        createBuildConfigurationPage.open(testData.getProject().getId()).setUrl(url).submit();
        createBuildConfigurationPage.setupBuildConfiguration(testData.getBuildType().getName());
        checkedWithSuperUser
            .getBuildConfigRequest()
            .get(getBuildConfigId(testData.getProject().getId(), testData.getBuildType().getName()));
    }

    @Test(groups = "UI")
    public void authorizedUserCanManuallyCreateBuildConfiguration() {
        var testData = testDataStorage.addTestData();
        loginAsUser(testData.getUser());
        new CheckedRequests(Specifications.getSpec().authSpec(testData.getUser()))
            .getProjectRequest()
            .create(testData.getProject());
        var createBuildConfigurationPage = new CreateBuildConfigurationPage();
        createBuildConfigurationPage.open(testData.getProject().getId())
                                    .clickOnManuallyButton()
                                    .setupBuildConfiguration(testData.getBuildType().getName());
        checkedWithSuperUser
            .getBuildConfigRequest()
            .get(getBuildConfigId(testData.getProject().getId(), testData.getBuildType().getName()));
    }
}
