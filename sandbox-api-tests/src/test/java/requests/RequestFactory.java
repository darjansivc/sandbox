package requests;

//import constants.payloads.commons.Commons;
//import io.qameta.allure.Step;

import constants.Path;
import io.restassured.response.Response;
import pojo.pojo_classes.LoginPojo;

import java.security.PublicKey;
//import org.apache.tika.io.IOUtils;
//import tests.TestBase;


public class RequestFactory {
    RestClient restClient = new RestClient();

    //  LOGIN
    public Response postLogin(Object body) {

        return restClient.doPostRequestSandboxLogin(body, "/api/users/login");
    }

    //  TECHNOLOGIES
    public Response postTechnology(Object body) {
        return restClient.doPostRequestSandbox(body, Path.TECHNOLOGY);
    }

    public Response putTechnology(Object body, Integer technologyId) {
        return restClient.doPutRequest(body, Path.TECHNOLOGY + technologyId);
    }

    public Response deleteTechnology(Integer technologyId) {
        return restClient.doDeleteRequest(Path.TECHNOLOGY + technologyId);
    }

    public Response getTechnologies() {
        return restClient.doGetRequestSandbox(Path.TECHNOLOGIES);
    }

    public Response getTechnology(Integer technologyId) {
        return restClient.doGetRequestSandbox(Path.TECHNOLOGY + technologyId);
    }

    //    OTHER

    public Response postProject(Object body) {
        return restClient.doPostRequestSandbox(body, Path.PROJECT);
    }

    public Response getProfile() {
        return restClient.doGetRequestSandbox("/api/profile");
    }

    public Response getAllUseCases() {
        return restClient.doGetRequestSandbox("/api/usecases/all");
    }

//testData.addLoginPayload(email, password)

}
