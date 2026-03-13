package Sesion04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Bai3 {

    // Class xử lý email
    static class UserProcessor {

        public String processEmail(String email) {

            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email");
            }

            String[] parts = email.split("@");

            if (parts.length != 2 || parts[1].isEmpty()) {
                throw new IllegalArgumentException("Invalid email");
            }

            return email.toLowerCase();
        }
    }

    private UserProcessor processor;

    // chạy trước mỗi test
    @BeforeEach
    void setUp() {
        processor = new UserProcessor();
    }

    @Test
    void testValidEmail() {

        // Arrange
        String email = "user@gmail.com";

        // Act
        String result = processor.processEmail(email);

        // Assert
        assertEquals("user@gmail.com", result);
    }

    @Test
    void testEmailMissingAt() {

        // Arrange
        String email = "usergmail.com";

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail(email);
        });
    }

    @Test
    void testEmailMissingDomain() {

        // Arrange
        String email = "user@";

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail(email);
        });
    }

    @Test
    void testEmailNormalizeLowercase() {

        // Arrange
        String email = "Example@Gmail.com";

        // Act
        String result = processor.processEmail(email);

        // Assert
        assertEquals("example@gmail.com", result);
    }
}