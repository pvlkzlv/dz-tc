package com.example.dztc.api;

import com.example.dztc.api.requests.checked.CheckedProject;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var project = new CheckedProject(testData.getUser()).create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}