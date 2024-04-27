package com.example.dztc.api.requests.unchecked;

import com.example.dztc.api.models.User;
import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.spec.Specifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UncheckedProject implements CrudInterface {
    public static final String PROJECT_ENDPOINT = "app/rest/projects";
    private User user;

    public UncheckedProject(User user) {
        this.user = user;
    }

    @Override
    public Response create(Object obj) {
        return RestAssured.given().spec(Specifications.getSpec().authSpec(user)).body(obj).post(PROJECT_ENDPOINT);
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
        return RestAssured.given().spec(Specifications.getSpec().authSpec(user)).delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}
