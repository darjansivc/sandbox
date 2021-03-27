package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.pojo_data_builders.TestDataBuilder;
import requests.RequestFactory;
import tests.test_data.TestData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static tests.test_data.TestData.TECHNOLOGY_NEW_TITLE;
import static utilities.Helper.technologyId;
import static utilities.Helper.token;

public class Technology {
    RequestFactory response = new RequestFactory();
    TestDataBuilder testData = new TestDataBuilder();

    @Test
    public void addTechnology() {
        Response responseBody = response.postTechnologies(testData.addTechnology(TestData.TECHNOLOGY_TITLE))
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'technology_title' field value.", jsonPath.get("technology_title"), equalTo(TestData.TECHNOLOGY_TITLE));
        assertThat("Unexpected 'technology_id' field value.", jsonPath.get("technology_id"), notNullValue());

        technologyId = jsonPath.get("technology_id");

        System.out.println("id: " + technologyId);

    }

    @Test(dependsOnMethods = "addTechnology")
    public void editTechnology() {
        Response responseBody = response.putTechnologies(testData.editTechnology(TECHNOLOGY_NEW_TITLE), technologyId)
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'technology_id' field value.", jsonPath.get("technology_id"), equalTo(technologyId));
        assertThat("Unexpected 'technology_title' field value.", jsonPath.get("technology_title"), equalTo(TECHNOLOGY_NEW_TITLE));
    }

    @Test(dependsOnMethods = "editTechnology")
    public void deleteTechnology() {
        Response responseBody = response.deleteTechnologies(technologyId)
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'Success' field value.", jsonPath.get("Success"), equalTo("Entry removed successfully"));
    }
}
