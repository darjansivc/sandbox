package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;
import pojo.pojo_data_builders.TestDataBuilder;
import requests.RequestFactory;
import tests.test_data.TestData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static tests.test_data.TestData.TECHNOLOGY_NEW_TITLE;
import static utilities.Helper.technologyId;

public class TechnologyTest {
    RequestFactory response = new RequestFactory();
    TestDataBuilder testData = new TestDataBuilder();

    @Test
    public void addTechnology() {
        Response responseBody = response.postTechnology(testData.generatePojoWithTechnology(TestData.TECHNOLOGY_TITLE))
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'technology_title' field value.", jsonPath.get("technology_title"), equalTo(TestData.TECHNOLOGY_TITLE));
        assertThat("Unexpected 'technology_id' field value.", jsonPath.get("technology_id"), notNullValue());

        technologyId = jsonPath.get("technology_id");

        System.out.println("id: " + technologyId);

    }

    @Test(dependsOnMethods = "addTechnology")
    public void getTechnology() {
        Response responseBody = response.getTechnology(technologyId)
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'technology_id' field value.", jsonPath.get("technology_id"), equalTo(technologyId));
        assertThat("Unexpected 'technology_title' field value.", jsonPath.get("technology_title"), equalTo(TestData.TECHNOLOGY_TITLE));
    }

    @Test(dependsOnMethods = "getTechnology")
    public void editTechnology() {
        Response responseBody = response.putTechnology(testData.generatePojoWithTechnology(TECHNOLOGY_NEW_TITLE), technologyId)
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'technology_id' field value.", jsonPath.get("technology_id"), equalTo(technologyId));
        assertThat("Unexpected 'technology_title' field value.", jsonPath.get("technology_title"), equalTo(TECHNOLOGY_NEW_TITLE));
    }

    @Test(dependsOnMethods = "editTechnology")
    public void deleteTechnology() {
        Response responseBody = response.deleteTechnology(technologyId)
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected 'Success' field value.", jsonPath.get("Success"), equalTo("Entry removed successfully"));
    }

    @Test(dependsOnMethods = "deleteTechnology")
    public void getTechnologyAfterDeletion() {
        Response responseBody = response.getTechnology(technologyId)
                .then().log().all()
                .statusCode(404).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        assertThat("Unexpected message for 'notechnology' field value.", jsonPath.get("notechnology"), equalTo("There is no technology with technology id " + technologyId));
    }

    @Test(dependsOnMethods = "deleteTechnology")
    public void verifyIfTechnologiesSectionIsEmpty() {
        Response responseBody = response.getTechnologies()
                .then()
                .statusCode(200).extract().response();

        ResponseBody body = responseBody.getBody();

        assertThat("Unexpected response value. Empty response expected.", body.asString(), equalTo("{}"));
    }
}
