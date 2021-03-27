package pojo.pojo_data_builders;

import pojo.pojo_classes.LoginPojo;
import pojo.pojo_classes.TechnologiesPojo;

public class TestDataBuilder {

    public LoginPojo addLoginPayload(String email, String password) {
        LoginPojo loginPojo = new LoginPojo();
        loginPojo.setEmail(email);
        loginPojo.setPassword(password);
        return loginPojo;
    }

    public TechnologiesPojo addTechnology(String name){
        TechnologiesPojo technologiesPojo =new TechnologiesPojo();
        technologiesPojo.setTechnology_title(name);
        return technologiesPojo;
    }

    public TechnologiesPojo editTechnology(String name){
        TechnologiesPojo technologiesPojo =new TechnologiesPojo();
        technologiesPojo.setTechnology_title(name);
        return technologiesPojo;
    }
}
