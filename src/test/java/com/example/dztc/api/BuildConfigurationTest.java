package com.example.dztc.api;

import java.util.Arrays;

import com.example.dztc.api.enums.Role;
import com.example.dztc.api.generators.TestDataGenerator;
import com.example.dztc.api.generators.TestDataStorage;
import com.example.dztc.api.models.Roles;
import com.example.dztc.api.requests.checked.CheckedProject;
import com.example.dztc.api.requests.checked.CheckedUser;
import com.example.dztc.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();
        new CheckedUser(Specifications.getSpec().superUserSpec()).create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser())).create(
            testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}