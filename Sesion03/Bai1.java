package Sesion03;

import java.util.*;

record User(String username, String email, String status) {
}

public class Bai1 {
    public static void main(String[] args) {

        List<User> users = new ArrayList<>();

        users.add(new User("alice", "alice@gmail.com", "ACTIVE"));
        users.add(new User("bob", "bob@gmail.com", "INACTIVE"));
        users.add(new User("charlie", "charlie@gmail.com", "ACTIVE"));

        // In danh sách user
        users.forEach(System.out::println);
    }
}