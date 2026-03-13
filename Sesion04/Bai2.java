package Sesion04;

public class Bai2 {

    public boolean checkRegistrationAge(int age) {

        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }

        if (age >= 18) {
            return true;
        }

        return false;
    }
}