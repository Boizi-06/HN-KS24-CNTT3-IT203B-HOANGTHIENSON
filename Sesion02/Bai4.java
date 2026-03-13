package Sesion02;

import java.util.*;
import java.util.function.*;

class User {
    private String username;

    public User() {
        this.username = "defaultUser";
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class Bai4 {
    public static void main(String[] args) {

        // Danh sách users
        List<User> users = Arrays.asList(
                new User("son"),
                new User("nam"),
                new User("linh"));

        // 1. Lambda: (user) -> user.getUsername()
        // Method Reference
        Function<User, String> getName = User::getUsername;

        users.stream()
                .map(getName)
                .forEach(System.out::println);

        // 2. Lambda: (s) -> System.out.println(s)
        // Method Reference
        Consumer<String> print = System.out::println;

        print.accept("Hello Java");

        // 3. Lambda: () -> new User()
        // Method Reference (Constructor)
        Supplier<User> createUser = User::new;

        User u = createUser.get();
        System.out.println(u.getUsername());
    }
}