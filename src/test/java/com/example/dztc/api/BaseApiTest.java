package com.example.dztc.api;

import com.example.dztc.api.generators.TestData;
import com.example.dztc.api.generators.TestDataGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest extends BaseTest {

    public TestDataGenerator testDataGenerator;
    public TestData testData;

    @BeforeMethod
    public void setUpTest() {
        testData = new TestDataGenerator().generate();
    }

    @AfterMethod
    public void cleanTest() {
        testData.delete();
    }
}