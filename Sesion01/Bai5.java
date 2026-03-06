package Sesion01;

public class Bai5 {

    // Custom Exception
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String msg) {
            super(msg);
        }
    }

    // User class
    static class User {
        private int age;

        public void setAge(int age) throws InvalidAgeException {
            if (age < 0) {
                throw new InvalidAgeException("Tuổi không thể âm!");
            }
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }

    public static void main(String[] args) {

        User user = new User();

        try {
            user.setAge(-5); // test lỗi
            System.out.println("Tuổi: " + user.getAge());
        } catch (InvalidAgeException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        System.out.println("Chương trình vẫn chạy tiếp...");
    }
}