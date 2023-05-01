package updateTests;

import io.restassured.response.ValidatableResponse;
import org.example.user.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateTest {
    User user;
    UserClient userClient;

    @Before
    public void setUp(){
        this.user = RandomUser.getRandomUser();
        this.userClient = new UserClient();
    }

    @Test
    public void updateUserTest(){
        userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(new UserCreds(user));

        UserData newUserData = new UserData("newemail" + user.getEmail(), "newname" + user.getName());

        ValidatableResponse updateResponse =
                userClient.update(loginResponse.extract().path("accessToken").toString().substring(7),
                        newUserData);
        //тут нужно удалить пользователя

        updateResponse.statusCode(200);
        assertEquals(updateResponse.extract().path("user.email").toString(), newUserData.getEmail());
        assertEquals(updateResponse.extract().path("user.name").toString(), newUserData.getName());
    }

    @Test
    public void updateUnautorizedUserTest(){
        userClient.create(user);
        UserData newUser = new UserData("newemail" + user.getEmail(), "newname" + user.getName());

        ValidatableResponse updateResponse =
                userClient.update("BadToken", newUser);
        //тут нужно удалить пользователя

        updateResponse.statusCode(403);
    }
}
