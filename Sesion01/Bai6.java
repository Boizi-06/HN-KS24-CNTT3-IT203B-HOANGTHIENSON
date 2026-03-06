package Sesion01;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bai6 {

    // Custom Exception
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String msg) {
            super(msg);
        }
    }

    // User class
    static class User {
        private String name;
        private int age;

        public User(String name) {
            this.name = name;
        }

        public void setAge(int age) throws InvalidAgeException {
            if (age < 0) {
                throw new InvalidAgeException("Tuổi không thể âm!");
            }
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    // Method giả lập ghi file (Checked Exception)
    public static void saveToFile(User user) throws IOException {
        // giả lập lỗi môi trường
        throw new IOException("Không thể ghi file!");
    }

    // Method ghi log lỗi
    public static void logError(String message) {
        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[ERROR] " + time + " - " + message);
    }

    public static void main(String[] args) {

        User user = new User("Son");

        // Defensive Programming: kiểm tra null trước khi dùng
        if (user != null) {
            System.out.println("Tên người dùng: " + user.getName());
        }

        try {

            // kiểm tra logic trước
            int ageInput = 20;
            if (ageInput < 0) {
                throw new InvalidAgeException("Tuổi không hợp lệ!");
            }

            user.setAge(ageInput);
            System.out.println("Tuổi: " + user.getAge());

            // gọi chức năng có thể lỗi môi trường
            saveToFile(user);

        } catch (InvalidAgeException e) {
            logError(e.getMessage());

        } catch (IOException e) {
            logError(e.getMessage());
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}