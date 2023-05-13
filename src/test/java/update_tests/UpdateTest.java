package update_tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateTest {
    User user;
    UserClient userClient;

    @Before
    public void setUp() {
        this.user = RandomUser.getRandomUser();
        this.userClient = new UserClient();
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя")
    public void updateUserTest() {
        userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(new UserCreds(user));

        UserData newUserData = new UserData("newemail" + user.getEmail(), "newname" + user.getName());

        ValidatableResponse updateResponse =
                userClient.update(loginResponse.extract().path("accessToken").toString().substring(7),
                        newUserData);

        updateResponse.statusCode(200);
        assertEquals(updateResponse.extract().path("user.email").toString(), newUserData.getEmail());
        assertEquals(updateResponse.extract().path("user.name").toString(), newUserData.getName());
    }

    @Test
    @DisplayName("Проверка обновления данных не авторизированного пользователя")
    public void updateUnautorizedUserTest() {
        userClient.create(user);
        UserData newUser = new UserData("newemail" + user.getEmail(), "newname" + user.getName());

        ValidatableResponse updateResponse =
                userClient.updateUnautorized(newUser);

        updateResponse.statusCode(401);
    }

    @After
    public void tearDown() {
        userClient.delete();
    }
}
