package org.example.user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.example.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String CREATE_PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String UPDATE_PATH = "api/auth/user";

    private List<String> tokens = new ArrayList<>();

    public UserClient() {
        RestAssured.baseURI = AppConfig.BASE_URI;
    }

    @Step("Регистрация пользователя")
    public ValidatableResponse create(User user) {
        ValidatableResponse createResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(CREATE_PATH)
                .then();

        if (createResponse.extract().statusCode() == 200) {
            tokens.add(createResponse.extract().path("accessToken").toString().substring(7));
        }

        return  createResponse;
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
    public ValidatableResponse update(String token, UserData userData) {
        return given()
                .auth().oauth2(token)
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .patch(UPDATE_PATH)
                .then();
    }

    @Step("Изменение данных не авторизированного пользователя")
    public ValidatableResponse updateUnautorized(UserData userData) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userData)
                .when()
                .patch(UPDATE_PATH)
                .then();
    }

    @Step("Удаление пользователей")
    public void delete() {
        for (String token : tokens) {
            given()
                    .auth().oauth2(token)
                    .header("Content-type", "application/json")
                    .and()
                    .when()
                    .delete(UPDATE_PATH)
                    .then();
        }
    }

    public String getToken(int index){
        return tokens.get(index);
    }
}
