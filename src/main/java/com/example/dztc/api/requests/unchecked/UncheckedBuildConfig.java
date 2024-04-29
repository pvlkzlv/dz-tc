package com.example.dztc.api.requests.unchecked;

import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedBuildConfig extends Request implements CrudInterface {

    public static final String BUILD_CONFIG_ENDPOINT = "app/rest/buildTypes";

    public UncheckedBuildConfig(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return RestAssured.given().spec(spec).body(obj).post(BUILD_CONFIG_ENDPOINT);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return RestAssured.given().spec(spec).delete(BUILD_CONFIG_ENDPOINT + "/id:" + id);
    }
}