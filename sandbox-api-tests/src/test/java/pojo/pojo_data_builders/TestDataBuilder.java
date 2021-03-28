package pojo.pojo_data_builders;

import pojo.pojo_classes.LoginPojo;
import pojo.pojo_classes.TechnologiesPojo;
import pojo.pojo_classes.UseCasesPojo;

import java.util.List;

public class TestDataBuilder {

    public LoginPojo addLoginPayload(String email, String password) {
        LoginPojo loginPojo = new LoginPojo();
        loginPojo.setEmail(email);
        loginPojo.setPassword(password);
        return loginPojo;
    }

    public UseCasesPojo addUseCase(String title, String description, String expectedResult, List<String> useCaseSteps, Boolean automated) {
        UseCasesPojo useCasesPojo = new UseCasesPojo();
        useCasesPojo.setTitle(title);
        useCasesPojo.setDescription(description);
        useCasesPojo.setExpected_result(expectedResult);
        useCasesPojo.setTeststeps(useCaseSteps);
        useCasesPojo.setAutomated(automated);
        return useCasesPojo;
    }

    public TechnologiesPojo addTechnology(String name) {
        TechnologiesPojo technologiesPojo = new TechnologiesPojo();
        technologiesPojo.setTechnology_title(name);
        return technologiesPojo;
    }

    public TechnologiesPojo editTechnology(String name) {
        TechnologiesPojo technologiesPojo = new TechnologiesPojo();
        technologiesPojo.setTechnology_title(name);
        return technologiesPojo;
    }
}
