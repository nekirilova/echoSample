package ru.mit.echo;

import io.restassured.response.ValidatableResponse;
import ru.mit.TestBase;

import static io.restassured.RestAssured.given;
import static ru.mit.echo.EchoEndpoints.*;

public class EchoMethods extends TestBase {
    public ValidatableResponse getEcho() {
        return given()
                .spec(getSpec())
                .when()
                .get(GET_INFO_PATH)
                .then();
    }

    public ValidatableResponse postEcho(Echo echo) {
        return given()
                .spec(getSpec())
                .when()
                .body(echo)
                .post(POST_INFO_PATH)
                .then();
    }
}
