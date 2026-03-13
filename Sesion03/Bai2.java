package Sesion03;

import java.util.*;

record User(String username, String email, String status) {
}

public class Bai2 {
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@yahoo.com", "INACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE"));

        // Lọc email gmail và in username
        users.stream()
                .filter(u -> u.email().endsWith("@gmail.com"))
                .map(User::username)
                .forEach(System.out::println);
    }
}