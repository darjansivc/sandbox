package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.pojo_data_builders.TestDataBuilder;
import requests.RequestFactory;
import tests.test_data.TestData;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static utilities.Helper.technologyId;

public class UseCaseTest {
    private static final int NUMBER_OF_ITERATIONS = 4;
    private JsonPath initialUseCaseResponse;

    RequestFactory response = new RequestFactory();
    TestDataBuilder testData = new TestDataBuilder();
    ArrayList<String> useCasesTitles = new ArrayList<>();
    ArrayList<Integer> usecaseIds = new ArrayList<>();

    @Test
    public void addUseCase() {
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            Response responseBody = response.postUseCase(testData.generatePojoWithUseCase(
                    TestData.TITLE + i,
                    TestData.DESCRIPTION + i,
                    TestData.EXPECTED_RESULT + i,
                    TestData.useCaseSteps, TestData.AUTOMATED))
                    .then().log().all()
                    .statusCode(200).extract().response();

            JsonPath jsonPath = responseBody.jsonPath();

            assertThat("Unexpected 'user_id' value.", jsonPath.get("user_id"), notNullValue());
            assertThat("Unexpected 'automated' value.", jsonPath.get("automated"), equalTo(TestData.AUTOMATED));
            assertThat("Unexpected 'title' value.", jsonPath.get("title"), equalTo(TestData.TITLE + i));
            assertThat("Unexpected 'expected_result' value.", jsonPath.get("expected_result"), equalTo(TestData.EXPECTED_RESULT + i));
            assertThat("Unexpected 'teststeps' value.", jsonPath.get("teststeps"), equalTo(TestData.useCaseSteps));

            //  These ArrayList values are used for verification that use cases are added on "Use cases" page.
            useCasesTitles.add(TestData.TITLE + i);
        }

    }

    @Test(dependsOnMethods = "addUseCase")
    public void verifyIfUseCasesAreAdded() {
        Response responseBody = response.getUseCases()
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = responseBody.jsonPath();

        List<Object> listOfIds = jsonPath.get("usecase_id");
        int listOfIdsSize = listOfIds.size();

        //  verify if previously added use cases exist in Use cases section
        for (String useCasesTitle : useCasesTitles) {
            for (int j = 0; j < listOfIdsSize; j++) {
                if (useCasesTitle.equals(jsonPath.get("[" + j + "].title"))) {
                    assertThat("Unexpected 'title' value. Should be equal.", jsonPath.get("[" + j + "].title"), equalTo(useCasesTitle));
                    usecaseIds.add(jsonPath.get("[" + j + "].usecase_id"));
                }
            }
        }
    }

    @Test(dependsOnMethods = "verifyIfUseCasesAreAdded")
    public void editUseCases() {
        for (Integer usecaseId : usecaseIds) {
            Response response = this.response.getUseCase(usecaseId)
                    .then().log().all()
                    .statusCode(200).extract().response();

            JsonPath jsonPath = response.jsonPath();
            initialUseCaseResponse = jsonPath;

            this.response.putUseCase(testData.generatePojoWithUseCase(
                    "This field previously had " + jsonPath.get("title").toString().length() + " characters ",
                    "This field previously had " + jsonPath.get("description").toString().length() + " characters ",
                    "This field previously had " + jsonPath.get("expected_result").toString().length() + " characters ",
                    TestData.useCaseSteps,
                    false), usecaseId)
                    .then().log().all()
                    .statusCode(200);
        }
    }

    @Test(dependsOnMethods = "editUseCases")
    public void verifyIfUseCaseIsEdited() {
        for (int i = 0; i < usecaseIds.size(); i++) {
            Response responseBody = response.getUseCase(usecaseIds.get(i))
                    .then().log().all()
                    .statusCode(200).extract().response();

            JsonPath currentUseCaseResponse = responseBody.jsonPath();

            assertThat("Unexpected 'title' field value. Values should not be equal.", currentUseCaseResponse.get("title"), not(equalTo(initialUseCaseResponse.get("title"))));
            assertThat("Unexpected 'description' field value. Values should not be equal.", currentUseCaseResponse.get("description"), not(equalTo(initialUseCaseResponse.get("description"))));
            assertThat("Unexpected 'expected_result' field value. Values should not be equal.", currentUseCaseResponse.get("expected_result"), not(equalTo(initialUseCaseResponse.get("expected_result"))));
        }


    }

    @Test(dependsOnMethods = "verifyIfUseCaseIsEdited")
    public void deleteCreatedUseCases() {
        for (Integer useCaseId : usecaseIds) {
            Response responseBody = response.deleteUseCase(useCaseId)
                    .then().log().all()
                    .statusCode(200).extract().response();

            JsonPath jsonPath = responseBody.jsonPath();

            assertThat("Unexpected 'Success' field value.", jsonPath.get("Success"), equalTo("Entry removed successfully"));
        }

    }

    @Test(dependsOnMethods = "deleteCreatedUseCases")
    public void verifyIfDeletedUseCasesExistInUseCaseSection() {
        int statusCode = response.getUseCases()
                .then()
                .extract().statusCode();

        if (statusCode == 200) {
            Response responseBody = response.getUseCases()
                    .then()
                    .extract().response();
            JsonPath jsonPath = responseBody.jsonPath();

            List<Object> responseUseCaseIds = jsonPath.get("usecase_id");
            int useCaseIdsListSize = responseUseCaseIds.size();

            if (responseUseCaseIds.size() > 0) {
                for (int i = 0; i < usecaseIds.size(); i++) {
                    for (int j = 0; j < useCaseIdsListSize; j++) {
                        assertThat("Unexpected result. Shouldn't be equal.", usecaseIds.get(i), not(equalTo(responseUseCaseIds.get(j))));

                    }
                }
            }
        } else if (statusCode == 204) {
            System.out.println("Use Case section is empty! No use cases written yet.");
        } else {
            System.out.println("Unexpected 'statusCode'. Current status code is " + statusCode + ", but we expected '200' or '204'");
        }
    }

    @Test(dependsOnMethods = "verifyIfDeletedUseCasesExistInUseCaseSection")
    public void verifyIfDeletedUseCaseExist() {
        for (Integer useCaseId : usecaseIds) {
            Response responseBody = response.getUseCase(useCaseId)
                    .then().log().ifValidationFails()
                    .statusCode(404).extract().response();

            JsonPath jsonPath = responseBody.jsonPath();

            assertThat("Unexpected message for 'nousecase' field value.", jsonPath.get("nousecase"), equalTo("There is no use case with use case id " + technologyId));
        }
    }

}



