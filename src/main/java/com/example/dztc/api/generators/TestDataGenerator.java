package com.example.dztc.api.generators;

import com.example.dztc.api.models.NewProjectDescription;
import com.example.dztc.api.models.Project;
import com.example.dztc.api.models.User;

public class TestDataGenerator {
    public TestData generate() {
        var user = User.builder().username("admin").password("admin").build();
        var projectDescription = NewProjectDescription
            .builder().parentProject(Project.builder().locator("_Root").build()).name(RandomData.getString()).id(RandomData.getString()).copyAllAssociatedSettings(true).build();
        return TestData.builder().user(user).project(projectDescription).build();
    }
}
