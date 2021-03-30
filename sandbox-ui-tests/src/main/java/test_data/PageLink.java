package test_data;

import utils.PropertyFile;

public class PageLink {
    private static final String BASE_URL = new PropertyFile().get("baseUrl");

    public static final String LOGIN = BASE_URL + "login";
    public static final String DASHBOARD = BASE_URL + "dashboard";
}
