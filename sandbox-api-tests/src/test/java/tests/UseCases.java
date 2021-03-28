package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.pojo_data_builders.TestDataBuilder;
import requests.RequestFactory;
import tests.test_data.TestData;

import static org.hamcrest.MatcherAssert.assertThat;

public class UseCases {
    RequestFactory response = new RequestFactory();
    TestDataBuilder testData = new TestDataBuilder();

    @Test
    public void addUseCase() {
        Response responseBody = response.postUseCase(testData.addUseCase(TestData.TITLE, TestData.DESCRIPTION, TestData.EXPECTED_RESULT, TestData.useCaseSteps, TestData.AUTOMATED))
                .then().log().all()
                .statusCode(200).extract().response();

//        JsonPath jsonPath = responseBody.jsonPath();
//
//        assertThat("Unexpected 'success' field value.", jsonPath.get("success"), equalTo(true));
//        assertThat("Unexpected 'token' field value.", jsonPath.get("token"), is(not(isEmptyString())));
//        assertThat("Unexpected 'refreshToken' field value.", jsonPath.get("refreshToken"), is(not(isEmptyString())));
//
//        token = "Bearer " + jsonPath.get("token");
    }

}

