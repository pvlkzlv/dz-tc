package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.User;
import com.example.dztc.api.requests.Request;
import com.example.dztc.api.spec.Specifications;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class AuthRequest extends Request {

    private User requestSpecification;

    public AuthRequest(RequestSpecification spec) {
        super(spec);
    }

    public String getCsrfToken() {
        var token = RestAssured
            .given()
            .spec(Specifications.getSpec().authSpec(requestSpecification))
            .get("authenticationTest.html?csrf")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .asString();
        return token;
    }
}