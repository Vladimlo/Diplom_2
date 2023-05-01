package org.example.user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.example.config.AppConfig;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String CREATE_PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String UPDATE_PATH = "api/auth/user";

    public UserClient(){
        RestAssured.baseURI = AppConfig.BASE_URI;
    }

    @Step("Регистрация пользователя")
    public ValidatableResponse create(User user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(CREATE_PATH)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse login(UserCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse update(String token, UserData userData){
        return given()
                .auth().oauth2(token)
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .patch(UPDATE_PATH)
                .then();
    }
}
