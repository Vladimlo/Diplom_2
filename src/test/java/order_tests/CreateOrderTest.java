package order_tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderClient;
import org.example.user.RandomUser;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CreateOrderTest {
    User user;
    UserClient userClient;
    OrderClient orderClient;

    Order order;

    @Before
    public void setUp() {
        user = RandomUser.getRandomUser();
        userClient = new UserClient();
        orderClient = new OrderClient();
        order = new Order();
    }

    @Test
    @DisplayName("Проверка создания заказа")
    public void createOrderTest() {
        order.setIngredients(orderClient.getValidIngridients());
        userClient.create(user);
        userClient.login(new UserCreds(user));
        ValidatableResponse createOrderResponse = orderClient.createOrderUnautorized(order);
        createOrderResponse.statusCode(200).and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания заказа не авторизированным пользователем")
    public void createOrderByUnauthorizedUser() {
        order.setIngredients(orderClient.getValidIngridients());
        userClient.create(user);
        userClient.login(new UserCreds(user));
        ValidatableResponse createOrderResponse = orderClient.createOrder("BadToken", order);
        createOrderResponse.statusCode(403);
    }

    @Test
    @DisplayName("Проверка создания заказа без ингридиентов")
    public void createOrderNonIngridients() {
        userClient.create(user);
        userClient.login(new UserCreds(user));
        ValidatableResponse createOrderResponse = orderClient.createOrder(userClient.getToken(0), order);
        createOrderResponse.statusCode(400).and().assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Проверка создания заказа с не существующими ингридиентами")
    public void createOrderInvalidHashIngridients() {
        order.setIngredients(orderClient.getInvalidIngridients());
        userClient.create(user);
        userClient.login(new UserCreds(user));
        ValidatableResponse createOrderResponse = orderClient.createOrder(userClient.getToken(0), order);
        createOrderResponse.statusCode(500);
    }

    @After
    public void tearDown() {
        userClient.delete();
    }
}
