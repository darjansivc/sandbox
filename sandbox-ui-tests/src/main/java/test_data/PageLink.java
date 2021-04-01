package test_data;

import utils.PropertyFile;

public class PageLink {
    private static final String BASE_URL = new PropertyFile().get("baseUrl");

    public static final String DASHBOARD = BASE_URL + "dashboard";
    public static final String USE_CASES = BASE_URL + "use-cases/";
}
