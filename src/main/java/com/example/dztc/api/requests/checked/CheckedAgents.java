package com.example.dztc.api.requests.checked;

import com.example.dztc.api.models.Agent;
import com.example.dztc.api.models.Agents;
import com.example.dztc.api.models.AuthorizedInfoModel;
import com.example.dztc.api.requests.Request;
import com.example.dztc.api.requests.unchecked.UncheckedAgents;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedAgents extends Request {
    public CheckedAgents(RequestSpecification spec) {
        super(spec);
    }

    public Agents get() {
        return new UncheckedAgents(spec)
            .get()
            .then()
            .assertThat().statusCode(HttpStatus.SC_OK)
            .extract()
            .as(Agents.class);
    }

    public String put(Agent agent, AuthorizedInfoModel authorizedInfoModel) {
        return new UncheckedAgents(spec)
            .put(agent, authorizedInfoModel)
            .then()
            .assertThat().statusCode(HttpStatus.SC_OK)
            .extract().body().asString();
    }
}
