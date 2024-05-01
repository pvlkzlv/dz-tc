package com.example.dztc.api.requests;

import com.example.dztc.api.requests.unchecked.UncheckedBuildConfig;
import com.example.dztc.api.requests.unchecked.UncheckedProject;
import com.example.dztc.api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {
    private final UncheckedUser userRequest;
    private final UncheckedProject projectRequest;
    private final UncheckedBuildConfig buildConfigRequest;

    public UncheckedRequests(RequestSpecification spec) {
        this.userRequest = new UncheckedUser(spec);
        this.projectRequest = new UncheckedProject(spec);
        this.buildConfigRequest = new UncheckedBuildConfig(spec);
    }
}