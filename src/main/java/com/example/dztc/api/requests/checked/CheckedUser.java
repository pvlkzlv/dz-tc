package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.User;
import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import com.example.dztc.api.requests.unchecked.UncheckedUser;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedUser extends Request implements CrudInterface {

    public CheckedUser(RequestSpecification spec) {
        super(spec);
    }

    @Override
    @Step("CREATE User request with body {0}")
    public User create(Object obj) {
        return new UncheckedUser(spec)
            .create(obj)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(User.class);
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
    @Step("DELETE User request with id {0}")
    public String delete(String id) {
        return new UncheckedUser(spec)
            .delete(id)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT)
            .extract()
            .asString();
    }
}