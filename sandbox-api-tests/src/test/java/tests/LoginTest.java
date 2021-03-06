package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.pojo_data_builders.TestDataBuilder;
import requests.RequestFactory;
import utilities.PropertyFile;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static utilities.Helper.token;

public class LoginTest {
    RequestFactory response = new RequestFactory();
    TestDataBuilder testData = new TestDataBuilder();
    private final String email = new PropertyFile().get("email");
    private final String password = new PropertyFile().get("password");

    @Test
    public void login() {
        Response responseBody = response.postLogin(testData.addLoginPayload(email, password))
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'success' field value.", jsonPath.get("success"), equalTo(true));
        assertThat("Unexpected 'token' field value.", jsonPath.get("token"), is(not(isEmptyString())));
        assertThat("Unexpected 'refreshToken' field value.", jsonPath.get("refreshToken"), is(not(isEmptyString())));

        token = "Bearer " + jsonPath.get("token");
    }

}

