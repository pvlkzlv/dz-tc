package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.Configuration;
import com.example.dztc.api.requests.CrudInterface;
import com.example.dztc.api.requests.Request;
import com.example.dztc.api.requests.unchecked.UncheckedAuthSettings;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedAuthSettings extends Request implements CrudInterface {
    public CheckedAuthSettings(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Object create(Object obj) {
        return null;
    }

    @Override
    public Configuration get(String id) {
        return new UncheckedAuthSettings(spec)
            .get(id)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(Configuration.class);
    }

    @Override
    public Configuration update(Object obj) {
        return new UncheckedAuthSettings(spec)
            .update(obj)
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(Configuration.class);
    }

    @Override
    public Object delete(String id) {
        return null;
    }
}
