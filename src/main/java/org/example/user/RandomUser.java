package org.example.user;

public class RandomUser {
    public static User getRandomUser() {
        return new User("test" + (1000 + (int) (Math.random() * 10000) + "@yandex.ru"),
                "test" + 1000 + (int) (Math.random() * 10000),
                "test" + 1000 + (int) (Math.random() * 10000));
    }
}