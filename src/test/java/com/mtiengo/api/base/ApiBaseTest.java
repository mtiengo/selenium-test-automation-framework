package com.mtiengo.api.base;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;

public class ApiBaseTest {

    protected static final String BASE_URI = System.getProperty("api.baseUri", "https://dummyjson.com");

    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI = BASE_URI;

        // Jackson configuration
        RestAssured.config = RestAssuredConfig.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .jackson2ObjectMapperFactory((type, charset) ->
                                new ObjectMapper()
                                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        ));

        // Full request/response logging for portfolio, for production use ErrorLoggingFilter only
        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL)
                // new ErrorLoggingFilter(LogDetail.ALL)
        );
    }
}
