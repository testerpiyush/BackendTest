package com.mytaxi.apitest.utility;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class RestUtil {

    public static Response callGet(String baseUrl, String path) {
        Response response = RestAssured.given().contentType(ContentType.JSON).log().all().when()
                .get(baseUrl + path);
        response.then().log().all();
        return response;
    }
}
