package com.example.dztc.api.requests.unchecked;

import static io.restassured.RestAssured.given;

import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedUser extends Request implements CrudInterface {
    private final static String USER_ENDPOINT = "/app/rest/users";

    public UncheckedUser(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given().spec(spec).body(obj).post(USER_ENDPOINT);
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
        return given().spec(spec).delete(USER_ENDPOINT + "/username:" + id);
    }
}