package com.example.dztc.api.requests;

import com.example.dztc.api.requests.unchecked.UncheckedAgents;
import com.example.dztc.api.requests.unchecked.UncheckedAuthSettings;
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
    private final UncheckedAuthSettings uncheckedAuthSettings;
    private final UncheckedAgents uncheckedAgents;

    public UncheckedRequests(RequestSpecification spec) {
        this.userRequest = new UncheckedUser(spec);
        this.projectRequest = new UncheckedProject(spec);
        this.buildConfigRequest = new UncheckedBuildConfig(spec);
        this.uncheckedAuthSettings = new UncheckedAuthSettings(spec);
        this.uncheckedAgents = new UncheckedAgents(spec);
    }
}