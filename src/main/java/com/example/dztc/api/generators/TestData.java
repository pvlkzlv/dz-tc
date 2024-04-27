package com.example.dztc.api.generators;

import com.example.dztc.api.models.NewProjectDescription;
import com.example.dztc.api.models.User;
import com.example.dztc.api.requests.unchecked.UncheckedProject;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;

    public void delete() {
        new UncheckedProject(user).delete(project.getId());
    }
}
