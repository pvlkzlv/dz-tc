package com.example.dztc.api.requests.unchecked;

import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedAuthSettings extends Request implements CrudInterface {
    private final static String AUTH_SETTINGS_ENDPOINT = "app/rest/server/authSettings";
    public UncheckedAuthSettings(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Object create(Object obj) {
        return null;
    }

    @Override
    @Step("GET Auth Setting request")
    public Response get(String id) {
        return RestAssured.given().spec(spec).get(AUTH_SETTINGS_ENDPOINT);
    }

    @Override
    @Step("UPDATE Auth Setting request")
    public Response update(Object obj) {
        return RestAssured.given().spec(spec).body(obj).put(AUTH_SETTINGS_ENDPOINT);
    }

    @Override
    public Object delete(String id) {
        return null;
    }
}
