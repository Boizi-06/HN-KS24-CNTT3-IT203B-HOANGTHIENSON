package Sesion01;

public class Bai3 {

    static class User {
        private int age;

        public void setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Tuổi không thể âm!");
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
            user.setAge(20);
            System.out.println("Tuổi hợp lệ: " + user.getAge());

            user.setAge(-5); // sẽ ném ngoại lệ
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}