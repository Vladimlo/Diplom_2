package orderTests;

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

public class GetOrderTest {
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
    @DisplayName("Проверка получения заказа авторизированным пользователем")
    public void getOrderTest() {
        order.setIngredients(orderClient.getValidIngridients());
        userClient.create(user);
        userClient.login(new UserCreds(user));
        ValidatableResponse getOrdersResponse = orderClient.getOrders(userClient.getToken(0));
        getOrdersResponse.statusCode(200).and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка получения заказа не авторизированным пользователем")
    public void getOrderUnautorizedTest() {
        order.setIngredients(orderClient.getValidIngridients());
        userClient.create(user);
        userClient.login(new UserCreds(user));
        ValidatableResponse getOrdersResponse = orderClient.getOrdersUnautorized();
        getOrdersResponse.statusCode(401);
    }

    @After
    public void tearDown() {
        userClient.delete();
    }
}
