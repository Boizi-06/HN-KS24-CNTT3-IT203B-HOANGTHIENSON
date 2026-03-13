package Sesion04;

public class Bai1 {

    public boolean isValidUsername(String username) {

        if (username == null) {
            return false;
        }

        // kiểm tra độ dài
        if (username.length() < 6 || username.length() > 20) {
            return false;
        }

        // kiểm tra khoảng trắng
        if (username.contains(" ")) {
            return false;
        }

        return true;
    }
}