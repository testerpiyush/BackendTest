package com.mytaxi.apitest.utility;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.mytaxi.apitest.constants.ApiConstants;

public class RestUtil {

    public static Response callGet(String baseUrl, String path) {
        Response response = RestAssured.given().contentType(ContentType.JSON).log().all().when()
                .get(baseUrl + path);
        response.then().statusCode(ApiConstants.STATUS_CODE_200).log().all();
        return response;
    }

    public static Response callGet(String baseUrl, String path, String paramName, String paramValue) {
        Response response = RestAssured.given().contentType(ContentType.JSON).queryParam(paramName, paramValue).log().all().when()
                .get(baseUrl + path);
        response.then().log().all();
        return response;
    }

    public static int statusCodeValidation(Response response) {
        if (ApiConstants.STATUS_CODE_200 == response.getStatusCode() || response.asString().contains("Error")) {
            ReportUtil.log("Verify Status code ", "Status code is 200", "info");
            return 200;
        } else if (ApiConstants.STATUS_CODE_400 == response.getStatusCode()) {
            ReportUtil.log("Verify Status code ", "Status code is 400" + response.asString(), "fail");
            return 400;
        } else if (ApiConstants.STATUS_CODE_500 == response.getStatusCode()) {
            ReportUtil.log("Verify Status code ", "Status code is 500" + response.asString(), "fail");
            return 500;
        } else if (ApiConstants.STATUS_CODE_504 == response.getStatusCode()) {
            ReportUtil.log("Verify Status code ", "Status code is 504" + response.asString(), "fail");
            return 504;
        } else if (ApiConstants.STATUS_CODE_502 == response.getStatusCode()) {
            ReportUtil.log("Verify Status code ", "Status code is 502" + response.asString(), "fail");
            return 502;
        }
        return 0;
    }

}
