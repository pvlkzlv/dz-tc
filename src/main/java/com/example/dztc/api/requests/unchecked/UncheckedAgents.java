package com.example.dztc.api.requests.unchecked;

import static io.restassured.RestAssured.given;

import com.example.dztc.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedAgents extends Request {

    private static final String AGENTS_ENDPOINT = "/app/rest/agents";

    public UncheckedAgents(RequestSpecification spec) {
        super(spec);
    }

    public Response get() {
        return given()
            .spec(spec)
            .get(AGENTS_ENDPOINT + "?locator=authorized:any");
    }

    public Response put(String id, String body) {
        return given()
            .spec(spec)
            .body(body)
            .contentType("text/plain")
            .accept("text/plain")
            .put(AGENTS_ENDPOINT + "/id:" + id + "/authorized");
    }
}