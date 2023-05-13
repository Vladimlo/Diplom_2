package user_tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.RandomUser;
import org.example.user.User;
import org.example.user.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class UserTest {
    User user;
    UserClient userClient;

    @Before
    public void setUp() {
        user = RandomUser.getRandomUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка создания пользователя")
    public void createUser() {
        ValidatableResponse createUserResponse = userClient.create(user);
        createUserResponse.statusCode(200).and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания существующего пользователя")
    public void cannotCreateIdenticalUsers() {
        userClient.create(user);

        ValidatableResponse createUserResponse = userClient.create(user);
        createUserResponse.statusCode(403);
    }

    @Test
    @DisplayName("Проверка создания пользователя без указания пароля")
    public void passwordIsRequariedToCreate() {
        ValidatableResponse createUserResponse = userClient.create(user.setPassword(null));
        createUserResponse.statusCode(403);
    }

    @After
    public void tearDown() {
        userClient.delete();
    }
}
