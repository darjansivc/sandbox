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

public class UseCases {
    RequestFactory response = new RequestFactory();
    TestDataBuilder testData = new TestDataBuilder();
    ArrayList<String> useCasesTitles = new ArrayList<String>();
    ArrayList<Integer> usecaseIds = new ArrayList<Integer>();
    private static final int numberOfIterations = 2;
    private JsonPath initialUseCaseResponse;

    @Test
    public void addUseCase() {
        for (int i = 0; i < numberOfIterations; i++) {
            Response responseBody = response.postUseCase(testData.addUseCase(
                    TestData.TITLE + i,
                    TestData.DESCRIPTION + i,
                    TestData.EXPECTED_RESULT + i,
                    TestData.useCaseSteps, TestData.AUTOMATED))
                    .then().log().all()
                    .statusCode(200).extract().response();

            JsonPath jsonPath = responseBody.jsonPath();

            //todo: dodaj poruke
            assertThat("", jsonPath.get("user_id"), notNullValue());
            assertThat("", jsonPath.get("automated"), equalTo(TestData.AUTOMATED));
            assertThat("", jsonPath.get("title"), equalTo(TestData.TITLE + i));
            assertThat("", jsonPath.get("expected_result"), equalTo(TestData.EXPECTED_RESULT + i));
            assertThat("", jsonPath.get("teststeps"), equalTo(TestData.useCaseSteps));

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

        List<Object> useCaseIds = jsonPath.get("usecase_id");
        int useCaseIdsNumber = useCaseIds.size();

        //  verify if previously added use cases exist in Use cases section
        for (String useCasesTitle : useCasesTitles) {
            for (int j = 0; j < useCaseIdsNumber; j++) {
                if (useCasesTitle.equals(jsonPath.get("[" + j + "].title"))) {
                    assertThat("Unexpected 'title' value. Should be equal.", jsonPath.get("[" + j + "].title"), equalTo(useCasesTitle));
                    usecaseIds.add(jsonPath.get("[" + j + "].usecase_id"));
                }
            }
        }
    }

    @Test(dependsOnMethods = "verifyIfUseCasesAreAdded")
    public void editFirstUseCase() {
        for (Integer usecaseId : usecaseIds) {
            Response response1 = response.getUseCase(usecaseId)
                    .then().log().all()
                    .statusCode(200).extract().response();

            JsonPath jsonPath = response1.jsonPath();
            initialUseCaseResponse = jsonPath;

            response.putUseCase(testData.addUseCase(
                    "This field previously had " + jsonPath.get("title").toString().length() + " characters ",
                    "This field previously had " + jsonPath.get("description").toString().length() + " characters ",
                    "This field previously had " + jsonPath.get("expected_result").toString().length() + " characters ",
                    TestData.useCaseSteps,
                    false), usecaseId)
                    .then().log().all()
                    .statusCode(200);
        }
    }

    @Test(dependsOnMethods = "editFirstUseCase")
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

    //todo: OPCIONO-PROVERI DA LI POSTOJE U LISTI

}



