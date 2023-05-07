package org.example.order;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.example.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String CREATE_PATH = "/api/orders";

    private final List<String> validIngridients = new ArrayList<>(List.of("61c0c5a71d1f82001bdaaa72",
            "61c0c5a71d1f82001bdaaa6f",
            "61c0c5a71d1f82001bdaaa6d"));

    private final List<String> invalidIngridients = new ArrayList<>(List.of("badIngridient1",
            "badIngridient2",
            "badIngridient3"));

    public List<String> getValidIngridients() {
        return validIngridients;
    }

    public List<String> getInvalidIngridients() {
        return invalidIngridients;
    }

    public OrderClient(){
        RestAssured.baseURI = AppConfig.BASE_URI;
    }

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String token, Order order){
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .and()
                .body(order)
                .when()
                .post(CREATE_PATH)
                .then();
    }

    @Step("Создание заказа не авторизированным пользователем")
    public ValidatableResponse createOrderUnautorized(Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_PATH)
                .then();
    }

    @Step("Получение заказов авторизированного пользователя")
    public ValidatableResponse getOrders(String token){
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .and()
                .when()
                .get(CREATE_PATH)
                .then();
    }

    @Step("Получение заказов не авторизированного пользователя")
    public ValidatableResponse getOrdersUnautorized(){
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(CREATE_PATH)
                .then();
    }
}
