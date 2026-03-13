package Sesion02;

import java.util.function.*;

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Role: " + role;
    }
}

public class Bai1 {
    public static void main(String[] args) {

        // 1. Predicate: kiểm tra User có phải Admin không
        Predicate<User> isAdmin = user -> user.getRole().equals("Admin");

        // 2. Function: chuyển User thành String username
        Function<User, String> getUsername = user -> user.getUsername();

        // 3. Consumer: in thông tin User ra màn hình
        Consumer<User> printUser = user -> System.out.println(user);

        // 4. Supplier: tạo User mới với giá trị mặc định
        Supplier<User> createDefaultUser = () -> new User("guest", "User");

        // Test
        User u1 = new User("son", "Admin");

        System.out.println("Is Admin: " + isAdmin.test(u1));
        System.out.println("Username: " + getUsername.apply(u1));
        printUser.accept(u1);

        User defaultUser = createDefaultUser.get();
        System.out.println("Default User:");
        printUser.accept(defaultUser);
    }
}