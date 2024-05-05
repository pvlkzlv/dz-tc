package com.example.dztc.api;

import com.example.dztc.api.generators.TestDataStorage;
import com.example.dztc.api.requests.CheckedRequests;
import com.example.dztc.api.requests.UncheckedRequests;
import com.example.dztc.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest extends BaseTest {

    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void setUpTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }
}