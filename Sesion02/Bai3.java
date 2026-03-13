package Sesion02;

@FunctionalInterface
interface Authenticatable {

    // Abstract method
    String getPassword();

    // Default method
    default boolean isAuthenticated() {
        return getPassword() != null && !getPassword().isEmpty();
    }

    // Static method
    static String encrypt(String rawPassword) {
        return "ENC_" + rawPassword;
    }
}

public class Bai3 {
    public static void main(String[] args) {

        // Sử dụng Lambda vì đây là Functional Interface
        Authenticatable user = () -> "123456";

        System.out.println("Password: " + user.getPassword());
        System.out.println("Authenticated: " + user.isAuthenticated());

        // Gọi static method của interface
        String encrypted = Authenticatable.encrypt("123456");
        System.out.println("Encrypted password: " + encrypted);
    }
}