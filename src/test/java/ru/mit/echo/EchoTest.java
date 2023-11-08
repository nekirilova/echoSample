package ru.mit.echo;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EchoTest {

    static EchoMethods methods;
    Echo echo;


    @BeforeAll
    public static void setUp() {
        methods = new EchoMethods();
    }

    @AfterAll
    public static void delete(){

    }

    @Order(1)
    @DisplayName("Запрос на получение данных возвращает статус код 200")
    @Test
    public void getEchoReturnsStatusCode200() {
        String expectedUrl = "https://postman-echo.com/get/";
        ValidatableResponse response = methods.getEcho();
        assertAll(
                ()-> assertEquals(HTTP_OK, response.extract().statusCode(),
                "Неправильный статус код"),
                ()-> assertEquals(expectedUrl, response.extract().body().path("url")));
    }
    @Order(2)
    @DisplayName("Запрос на создание данных возвращает статус код 200")
    @ParameterizedTest
    @CsvSource(value = {
            "test1, 1",
            "test2, 2",
            "test3, 3"
    })
    public void postEchoReturnsStatusCode200(String test, int count) {
        echo = new Echo(test, count);
        ValidatableResponse response = methods.postEcho(echo);
        assertAll(
                ()-> assertEquals(HTTP_OK, response.extract().statusCode(),
                        "Неправильный статус код"),
                ()-> assertEquals(test, response.extract().body().path("data.test")),
                ()-> assertEquals(count, (int) response.extract().body().path("data.count")));
    }
}
