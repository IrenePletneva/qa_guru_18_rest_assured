package in.regress.tests;

import in.regress.models.LoginBodyModel;
import in.regress.models.LoginResponseModel;
import in.regress.models.LoginErrorModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.regress.specs.LoginSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @Test
    @DisplayName("Проверка успешной авторизации с Email и Password")
    void successfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Отправляем запрос на успешную Авторизацию", () ->
                given(authorizationRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(authorizationResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("Проверка ответа на запрос об успешной Авторизации", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации без Email")
    void authorizationWithOutEmailTest() {

        LoginBodyModel noneEmailData = new LoginBodyModel();
        noneEmailData.setPassword("cityslicka");

        LoginErrorModel response = step("Отправляем запрос без Email на авторизацию", () ->
                given(authorizationRequestSpec)
                        .body(noneEmailData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(errorAuthorizationResponseSpec)
                        .extract().as(LoginErrorModel.class));

        step("Проверка ответа с ошибкой на запрос Авторизации", () ->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации без Password")
    void authorizationWithOutPasswordTest() {

        LoginBodyModel nonePasswordData = new LoginBodyModel();
        nonePasswordData.setEmail("eve.holt@reqres.in");

        LoginErrorModel response = step("Отправляем запрос без Password на авторизацию", () ->
                given(authorizationRequestSpec)
                        .body(nonePasswordData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(errorAuthorizationResponseSpec)
                        .extract().as(LoginErrorModel.class));

        step("Проверка ответа с ошибкой на запрос Авторизации", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации с данными неизвестного пользователя")
    void undefinedUserAuthorizationTest() {

        LoginBodyModel undefinedUserData = new LoginBodyModel();
        undefinedUserData.setEmail("test@gmail.com");
        undefinedUserData.setPassword("123456");

        LoginErrorModel response = step("Отправляем запрос с неизвестным пользователем на авторизацию", () ->
                given(authorizationRequestSpec)
                        .body(undefinedUserData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(errorAuthorizationResponseSpec)
                        .extract().as(LoginErrorModel.class));

        step("Проверка ответа с ошибкой на запрос Авторизации", () ->
                assertEquals("user not found", response.getError()));
    }
}
