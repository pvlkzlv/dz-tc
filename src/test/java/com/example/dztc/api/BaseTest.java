package com.example.dztc.api;

import com.example.dztc.api.generators.TestDataStorage;
import com.example.dztc.api.requests.CheckedRequests;
import com.example.dztc.api.requests.UncheckedRequests;
import com.example.dztc.api.spec.Specifications;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());
    protected SoftAssertions softy;

    @BeforeMethod
    public void setUpTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
        testDataStorage.delete();
    }
}