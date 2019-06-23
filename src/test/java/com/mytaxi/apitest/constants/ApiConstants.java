package com.mytaxi.apitest.constants;

public class ApiConstants {
    public static final int STATUS_CODE_200 = 200;
    public static final int STATUS_CODE_201 = 201;
    public static final int STATUS_CODE_400 = 400;
    public static final int STATUS_CODE_401 = 401;
    public static final int STATUS_CODE_404 = 404;
    public static final int STATUS_CODE_500 = 500;
    public static final int STATUS_CODE_408 = 408;
    public static final int STATUS_CODE_403 = 403;
    public static final int STATUS_CODE_504 = 504;
    public static final int STATUS_CODE_502 = 502;

    public static final String TEST_RESOURCE_DIR = new StringBuilder(System.getProperty("user.dir"))
            .append(System.getProperty("file.separator"))
            .append("src").append(System.getProperty("file.separator"))
            .append("test").append(System.getProperty("file.separator"))
            .append("resources").append(System.getProperty("file.separator"))
            .append("props").append(System.getProperty("file.separator"))
            .toString();
}
