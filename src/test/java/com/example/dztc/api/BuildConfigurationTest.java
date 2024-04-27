package com.example.dztc.api;

import com.example.dztc.api.models.User;
import com.example.dztc.api.spec.Specifications;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var user = User.builder().username("admin").password("admin").build();

        var token = RestAssured
            .given()
            .spec(Specifications.getSpec().authSpec(user))
            .get("authenticationTest.html?csrf")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .asString();
        System.out.println(token);
    }
}