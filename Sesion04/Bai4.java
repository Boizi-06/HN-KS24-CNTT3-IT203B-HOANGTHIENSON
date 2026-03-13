package Sesion04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Bai4 {

    // Class xử lý mật khẩu
    static class PasswordService {

        public String evaluatePasswordStrength(String password) {

            if (password == null || password.length() < 8) {
                return "Yếu";
            }

            boolean hasUpper = password.matches(".*[A-Z].*");
            boolean hasLower = password.matches(".*[a-z].*");
            boolean hasNumber = password.matches(".*[0-9].*");
            boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");

            if (hasUpper && hasLower && hasNumber && hasSpecial) {
                return "Mạnh";
            }

            if (hasUpper || hasLower || hasNumber || hasSpecial) {
                return "Trung bình";
            }

            return "Yếu";
        }
    }

    PasswordService service = new PasswordService();

    @Test
    void testStrongPassword() {
        assertEquals("Mạnh", service.evaluatePasswordStrength("Abc123!@"));
    }

    @Test
    void testMissingUppercase() {
        assertEquals("Trung bình", service.evaluatePasswordStrength("abc123!@"));
    }

    @Test
    void testMissingLowercase() {
        assertEquals("Trung bình", service.evaluatePasswordStrength("ABC123!@"));
    }

    @Test
    void testMissingNumber() {
        assertEquals("Trung bình", service.evaluatePasswordStrength("Abcdef!@"));
    }

    @Test
    void testMissingSpecialChar() {
        assertEquals("Trung bình", service.evaluatePasswordStrength("Abc12345"));
    }

    @Test
    void testTooShortPassword() {
        assertEquals("Yếu", service.evaluatePasswordStrength("Ab1!"));
    }

    @Test
    void testOnlyLowercase() {
        assertEquals("Yếu", service.evaluatePasswordStrength("password"));
    }

    @Test
    void testUppercaseAndNumberOnly() {
        assertEquals("Yếu", service.evaluatePasswordStrength("ABC12345"));
    }
}