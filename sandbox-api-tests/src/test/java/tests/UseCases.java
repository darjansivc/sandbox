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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utilities.Helper.technologyId;

public class UseCases {
    RequestFactory response = new RequestFactory();
    TestDataBuilder testData = new TestDataBuilder();
    ArrayList<String> useCasesTitles = new ArrayList<String>();
    ArrayList<Integer> usecaseIds = new ArrayList<Integer>();
    private static final int numberOfIterations = 2;

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
                    System.out.println("usecase_id: " + jsonPath.get("[" + j + "].usecase_id") + " - " + useCasesTitle);
                    usecaseIds.add(jsonPath.get("[" + j + "].usecase_id"));
                }
            }
        }
        //todo: malo bolje assertacije
    }

    @Test(dependsOnMethods = "verifyIfUseCasesAreAdded")
    public void editFirstUseCase() {
        Response response1 = response.getUseCase(usecaseIds.get(0))
                .then().log().all()
                .statusCode(200).extract().response();

        JsonPath jsonPath = response1.jsonPath();

        Response response2 = response.putUseCase(testData.addUseCase(
                "This field previously had " + jsonPath.get("title").toString().length() + " characters ",
                "This field previously had " + jsonPath.get("description").toString().length() + " characters ",
                "This field previously had " + jsonPath.get("expected_result").toString().length() + " characters ",
                TestData.useCaseSteps,
                false), usecaseIds.get(0))
                .then().log().all()
                .statusCode(200).extract().response();

        //todo: assertacije

    }

    @Test(dependsOnMethods = "editFirstUseCase")
    public void deleteCreatedUseCases(){
        for (Integer useCaseId : usecaseIds) {
            Response responseBody = response.deleteUseCase(useCaseId)
                    .then().log().all()
                    .statusCode(200).extract().response();

            JsonPath jsonPath = responseBody.jsonPath();

            assertThat("Unexpected 'Success' field value.", jsonPath.get("Success"), equalTo("Entry removed successfully"));
        }

    }

}



