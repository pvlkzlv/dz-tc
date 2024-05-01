package com.example.dztc.api.requests;

import com.example.dztc.api.requests.checked.CheckedBuildConfig;
import com.example.dztc.api.requests.checked.CheckedProject;
import com.example.dztc.api.requests.checked.CheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private final CheckedUser userRequest;
    private final CheckedProject projectRequest;
    private final CheckedBuildConfig buildConfigRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.userRequest = new CheckedUser(spec);
        this.projectRequest = new CheckedProject(spec);
        this.buildConfigRequest = new CheckedBuildConfig(spec);
    }
}