package com.example.dztc.api.requests.unchecked;

import static io.restassured.RestAssured.given;

import com.example.dztc.api.models.Agent;
import com.example.dztc.api.models.AuthorizedInfoModel;
import com.example.dztc.api.requests.Request;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedAgents extends Request {

    private static final String AGENTS_ENDPOINT = "/app/rest/agents";

    public UncheckedAgents(RequestSpecification spec) {
        super(spec);
    }

    @Step("GET Agents request")
    public Response get() {
        return given()
            .spec(spec)
            .get(AGENTS_ENDPOINT + "?locator=defaultFilter");
    }

    @Step("PUT Agents request with agent {0} and authorized info {1}")
    public Response put(Agent agent, AuthorizedInfoModel authorizedInfo) {
        return given()
            .spec(spec)
            .body(authorizedInfo)
            .put(AGENTS_ENDPOINT + "/id:" + agent + "/authorizedInfo?");
    }
}