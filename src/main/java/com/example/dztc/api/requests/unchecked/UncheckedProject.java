package com.example.dztc.api.requests.unchecked;

import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedProject extends Request implements CrudInterface {
    public static final String PROJECT_ENDPOINT = "app/rest/projects";

    public UncheckedProject(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return RestAssured.given().spec(spec).body(obj).post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return RestAssured.given().spec(spec).get(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return RestAssured.given().spec(spec).delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}