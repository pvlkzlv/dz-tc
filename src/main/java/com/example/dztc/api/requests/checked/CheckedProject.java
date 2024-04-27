package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.Project;
import com.example.dztc.api.models.User;
import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.unchecked.UncheckedProject;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

public class CheckedProject implements CrudInterface {

    private final User user;

    public CheckedProject(User user) {
        this.user = user;
    }

    @Override
    public Project create(Object obj) {
        return new UncheckedProject(user)
            .create(obj)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(Project.class);
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
    public Object delete(String id) {
        return new UncheckedProject(user).delete(id).then().assertThat().statusCode(HttpStatus.SC_OK).extract().asString();
    }
}
