package login_tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.RandomUser;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
    User user;
    UserClient userClient;

    @Before
    public void setUp() {
        user = RandomUser.getRandomUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка авторизации существующего пользователя")
    public void loginUserTest() {
        userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(new UserCreds(user));
        loginResponse.statusCode(200);
    }

    @Test
    @DisplayName("Проверка авторизации не существующего пользователя")
    public void userNotExist() {
        ValidatableResponse loginResponse = userClient.login(new UserCreds(user));
        loginResponse.statusCode(401);
    }

    @After
    public void tearDown() {
        userClient.delete();
    }
}
