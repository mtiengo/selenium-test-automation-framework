package com.mtiengo.api.tests;

import com.mtiengo.api.base.ApiBaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckTest extends ApiBaseTest {

    @Test
    public void testEndpointReturnsOkStatus() {
        given()
            .when()
                    .get("/test")
            .then()
                    .statusCode(200)
                    .body("status", equalTo("ok"))
                    .body("method", equalTo("GET"));
    }
}
