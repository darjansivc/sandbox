package requests;



import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.pojo_classes.LoginPojo;
import pojo.pojo_data_builders.TestDataBuilder;
import utilities.PropertyFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import static utilities.Helper.token;

import static io.restassured.RestAssured.given;


public class RestClient{
    private final String BASE_URL = new PropertyFile().get("baseURL");

    public Response doPostRequestWithoutAuthorization(Object body, String endPoint) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post(BASE_URL + endPoint);
    }

    public Response doPostRequestWithAuthorization(Object body, String endPoint) {
        return given().header("authorization", token).header("Content-Type", "application/json").log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post(BASE_URL + endPoint);
    }

    public Response doGetRequest(String endPoint) {
        return given().header("authorization", token).header("Content-Type", "application/json").log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL+ endPoint);
    }

    public Response doPutRequest(Object body, String endPoint){
        return given().header("authorization", token).header("Content-Type", "application/json").log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .put(BASE_URL+ endPoint);
    }

    public Response doDeleteRequest(String endPoint){
        return given().header("authorization", token).header("Content-Type", "application/json").log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URL+ endPoint);
    }


}