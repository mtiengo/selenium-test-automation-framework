package com.mtiengo.api.tests;

import com.mtiengo.api.base.ApiBaseTest;
import com.mtiengo.api.models.Product;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateProductTest extends ApiBaseTest {

    @Test
    public void createProductReturnsIdAndEchoesPayload() {
        Product payload = new Product(
                null,
                "Test Product",
                "A product created via automated test",
                "smartphones",
                199.99,
                10.0,
                4.5,
                42,
                "TestBrand",
                "TST-001"
        );

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Test Product"))
                .body("category", equalTo("smartphones"))
                .body("brand", equalTo("TestBrand"))
                .body("stock", equalTo(42))
                .extract().response();

        // DummyJSON returns the created product at the JSON root (no wrapping node)
        Product created = response.jsonPath().getObject("$", Product.class);

        assertThat(created.id()).isNotNull().isPositive();
        assertThat(created.title()).isEqualTo("Test Product");
        assertThat(created.description()).isEqualTo("A product created via automated test");
        assertThat(created.category()).isEqualTo("smartphones");
        assertThat(created.price()).isEqualTo(199.99);
        assertThat(created.discountPercentage()).isEqualTo(10.0);
        assertThat(created.rating()).isEqualTo(4.5);
        assertThat(created.stock()).isEqualTo(42);
        assertThat(created.brand()).isEqualTo("TestBrand");
    }
}
