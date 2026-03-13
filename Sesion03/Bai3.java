package Sesion03;

import java.util.*;

record User(String username, String email, String status) {
}

class UserRepository {

    List<User> users = List.of(
            new User("alice", "alice@gmail.com", "ACTIVE"),
            new User("bob", "bob@yahoo.com", "INACTIVE"),
            new User("charlie", "charlie@gmail.com", "ACTIVE"));

    public Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equals(username))
                .findFirst();
    }
}

public class Bai3 {
    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

        Optional<User> user = repo.findUserByUsername("alice");

        user.ifPresent(u -> System.out.println("Welcome " + u.username()));

        String result = user
                .map(u -> "Welcome " + u.username())
                .orElse("Guest login");

        System.out.println(result);
    }
}