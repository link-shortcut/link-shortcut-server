package kr.lnsc.api.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class HttpMethodFixture {

    public static ExtractableResponse<Response> httpGet(String path) {
        return RestAssured
                .given().redirects().follow(false).log().all()
                .when().get(path)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> httpPost(Object postRequest, String path) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(postRequest)
                .when().post(path)
                .then().log().all()
                .extract();
    }

    public static String getExceptionMessage(ExtractableResponse<Response> response) {
        return response.jsonPath().getString("message");
    }
}
