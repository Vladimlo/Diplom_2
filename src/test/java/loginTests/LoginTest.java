package loginTests;

import io.restassured.response.ValidatableResponse;
import org.example.user.RandomUser;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserCreds;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
    User user;
    UserClient userClient;

    @Before
    public void setUp(){
        user = RandomUser.getRandomUser();
        userClient = new UserClient();
    }

    @Test
    public void loginUserTest(){
        userClient.create(user);
        //тут нужно будет удалить пользователя
        ValidatableResponse loginResponse = userClient.login(new UserCreds(user));
        loginResponse.statusCode(200);
    }

    @Test
    public void userNotExist(){
        ValidatableResponse loginResponse = userClient.login(new UserCreds(user));
        loginResponse.statusCode(401);
    }
}
