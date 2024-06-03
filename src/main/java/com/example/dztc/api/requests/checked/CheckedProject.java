package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.Project;
import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import com.example.dztc.api.requests.unchecked.UncheckedProject;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProject extends Request implements CrudInterface {

    public CheckedProject(RequestSpecification spec) {
        super(spec);
    }

    @Override
    @Step("CREATE Project request")
    public Project create(Object obj) {
        return new UncheckedProject(spec)
            .create(obj)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(Project.class);
    }

    @Override
    @Step("GET Project request")
    public Object get(String id) {
        return new UncheckedProject(spec)
            .get(id)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(Project.class);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    @Step("DELETE Project request")
    public String delete(String id) {
        return new UncheckedProject(spec)
            .delete(id)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT)
            .extract()
            .asString();
    }
}