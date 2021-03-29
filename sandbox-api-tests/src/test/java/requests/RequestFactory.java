package requests;

import constants.Path;
import io.restassured.response.Response;

public class RequestFactory {
    RestClient restClient = new RestClient();

    //  LOGIN
    public Response postLogin(Object body) {

        return restClient.doPostRequestWithoutAuthorization(body, "/api/users/login");
    }

    //  USE CASES
    public Response postUseCase(Object body){
        return restClient.doPostRequestWithAuthorization(body, Path.USE_CASE);
    }

    public Response getUseCases(){
        return restClient.doGetRequest(Path.USE_CASES_ALL);
    }

    public Response getUseCase(Integer useCaseId){
        return restClient.doGetRequest(Path.USE_CASES_SPECIFIC + useCaseId);
    }

    public Response putUseCase(Object body, Integer useCaseId){
        return restClient.doPutRequest(body, Path.USE_CASE + useCaseId);
    }

    public Response deleteUseCase(Integer useCaseId) {
        return restClient.doDeleteRequest(Path.USE_CASE + useCaseId);
    }

    //  TECHNOLOGIES
    public Response postTechnology(Object body) {
        return restClient.doPostRequestWithAuthorization(body, Path.TECHNOLOGY);
    }

    public Response putTechnology(Object body, Integer technologyId) {
        return restClient.doPutRequest(body, Path.TECHNOLOGY + technologyId);
    }

    public Response deleteTechnology(Integer technologyId) {
        return restClient.doDeleteRequest(Path.TECHNOLOGY + technologyId);
    }

    public Response getTechnologies() {
        return restClient.doGetRequest(Path.TECHNOLOGIES);
    }

    public Response getTechnology(Integer technologyId) {
        return restClient.doGetRequest(Path.TECHNOLOGY + technologyId);
    }




}
