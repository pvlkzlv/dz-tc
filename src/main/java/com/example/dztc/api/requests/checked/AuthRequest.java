package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.User;
import com.example.dztc.api.spec.Specifications;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

public class AuthRequest {
    private User user;
    public AuthRequest(User user) {
        this.user = user;
    }

    public String getCsrfToken() {
        var token = RestAssured
            .given()
            .spec(Specifications.getSpec().authSpec(user))
            .get("authenticationTest.html?csrf")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .asString();
        return token;
    }
}
