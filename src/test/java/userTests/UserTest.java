package userTests;

import io.restassured.response.ValidatableResponse;
import org.example.user.RandomUser;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserCreds;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class UserTest {
    User user;
    UserClient userClient;

    @Before
    public void setUp(){
        user = RandomUser.getRandomUser();
        userClient = new UserClient();
    }

    @Test
    public void createUser(){
        ValidatableResponse createUserResponse = userClient.create(user);
        createUserResponse.statusCode(200).and().assertThat().body("success", equalTo(true));
        //тут нужно удалить пользователя
    }

    @Test
    public void  cannotCreateIdenticalUsers(){
        userClient.create(user);
        //тут нужно удалить пользователя

        ValidatableResponse createUserResponse = userClient.create(user);
        //тут нужно удалить пользователя
        createUserResponse.statusCode(403);
    }

    @Test
    public void passwordIsRequariedToCreate(){
        ValidatableResponse createUserResponse = userClient.create(user.setPassword(null));
        createUserResponse.statusCode(403);
        //тут нужно удалить пользователя
    }
}
