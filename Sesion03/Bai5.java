package Sesion03;

import java.util.*;

record User(String username, String email, String status) {
}

public class Bai5 {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alexander", "a@gmail.com", "ACTIVE"),
                new User("bob", "b@gmail.com", "ACTIVE"),
                new User("charlotte", "c@gmail.com", "ACTIVE"),
                new User("Benjamin", "d@gmail.com", "ACTIVE"),
                new User("tom", "e@gmail.com", "ACTIVE"));

        // Top 3 username dài nhất
        users.stream()
                .sorted(Comparator.comparingInt(u -> ((User) u).username().length()).reversed())
                .limit(3)
                .map(User::username)
                .forEach(System.out::println);
    }
}