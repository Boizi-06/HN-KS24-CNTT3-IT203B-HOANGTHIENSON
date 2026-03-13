package Sesion02;

@FunctionalInterface
interface UserProcessor {
    String process(User u);
}

class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

class UserUtils {

    // static method dùng cho Method Reference
    public static String convertToUpperCase(User u) {
        return u.getUsername().toUpperCase();
    }
}

public class Bai6 {
    public static void main(String[] args) {

        // Method Reference tới static method
        UserProcessor processor = UserUtils::convertToUpperCase;

        User user = new User("son");

        String result = processor.process(user);

        System.out.println(result);
    }
}