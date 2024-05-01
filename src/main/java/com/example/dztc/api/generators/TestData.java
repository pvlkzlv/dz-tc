package com.example.dztc.api.generators;

import com.example.dztc.api.models.BuildType;
import com.example.dztc.api.models.NewProjectDescription;
import com.example.dztc.api.models.User;
import com.example.dztc.api.requests.unchecked.UncheckedProject;
import com.example.dztc.api.requests.unchecked.UncheckedUser;
import com.example.dztc.api.spec.Specifications;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public void delete() {
        var spec = Specifications.getSpec().authSpec(user);
        new UncheckedProject(spec).delete(project.getId());
        new UncheckedUser(spec).delete(user.getUsername());
    }
}