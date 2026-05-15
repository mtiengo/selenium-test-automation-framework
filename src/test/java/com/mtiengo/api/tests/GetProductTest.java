package com.mtiengo.api.tests;

import com.mtiengo.api.base.ApiBaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetProductTest extends ApiBaseTest {

    @Test
    public void getAllProductsReturnsPaginatedList() {
        given()
                .when()
                    .get("/products")
                .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("products", is(not(empty())))
                    .body("total", greaterThan(0))
                    .body("limit", equalTo(30))
                    .body("products[0].id", notNullValue());
    }

    @Test
    public void getProductByIdReturnsExpectedSchema() {
        given().pathParam("id", 1)
                .when()
                    .get("/products/{id}")
                .then()
                    .statusCode(200)
                    .body("id", equalTo(1))
                    .body("title", notNullValue())
                    .body("price", greaterThan(0f))
                    .body("category", notNullValue())
                    .body("rating", greaterThanOrEqualTo(0f));
    }
}
