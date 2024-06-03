package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.BuildType;
import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import com.example.dztc.api.requests.unchecked.UncheckedBuildConfig;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedBuildConfig extends Request implements CrudInterface {
    public CheckedBuildConfig(RequestSpecification spec) {
        super(spec);
    }

    @Override
    @Step("CREATE Build Configuration request with body {0}")
    public BuildType create(Object obj) {
        return new UncheckedBuildConfig(spec)
            .create(obj)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(BuildType.class);
    }

    @Override
    @Step("GET Build Configuration request with id {0}")
    public BuildType get(String id) {
        return new UncheckedBuildConfig(spec)
            .get(id)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(BuildType.class);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    @Step("DELETE Build Configuration request with id {0}")
    public Object delete(String id) {
        return new UncheckedBuildConfig(spec)
            .delete(id)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT)
            .extract()
            .as(BuildType.class);
    }
}