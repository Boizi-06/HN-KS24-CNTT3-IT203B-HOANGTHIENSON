package Sesion04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bai6 {

    // ================= ENTITY =================

    static class User {
        String email;
        LocalDate birthDate;

        User(String email, LocalDate birthDate) {
            this.email = email;
            this.birthDate = birthDate;
        }
    }

    static class UserProfile {
        String email;
        LocalDate birthDate;

        UserProfile(String email, LocalDate birthDate) {
            this.email = email;
            this.birthDate = birthDate;
        }
    }

    // ================= SERVICE =================

    static class UserService {

        public User updateProfile(User existingUser, UserProfile newProfile, List<User> allUsers) {

            // kiểm tra ngày sinh tương lai
            if (newProfile.birthDate.isAfter(LocalDate.now())) {
                return null;
            }

            // kiểm tra email trùng
            if (!newProfile.email.equals(existingUser.email)) {
                for (User u : allUsers) {
                    if (u.email.equals(newProfile.email)) {
                        return null;
                    }
                }
            }

            // cập nhật
            existingUser.email = newProfile.email;
            existingUser.birthDate = newProfile.birthDate;

            return existingUser;
        }
    }

    UserService service = new UserService();

    // ================= TEST =================

    @Test
    void updateProfile_success_whenEmailAndBirthValid() {

        User existingUser = new User("old@gmail.com", LocalDate.of(2000, 1, 1));
        UserProfile newProfile = new UserProfile("new@gmail.com", LocalDate.of(2000, 1, 1));

        List<User> users = new ArrayList<>();

        User result = service.updateProfile(existingUser, newProfile, users);

        assertNotNull(result);
    }

    @Test
    void updateProfile_fail_whenBirthDateInFuture() {

        User existingUser = new User("user@gmail.com", LocalDate.of(2000, 1, 1));
        UserProfile newProfile = new UserProfile("new@gmail.com", LocalDate.now().plusDays(1));

        List<User> users = new ArrayList<>();

        User result = service.updateProfile(existingUser, newProfile, users);

        assertNull(result);
    }

    @Test
    void updateProfile_fail_whenEmailDuplicated() {

        User existingUser = new User("user@gmail.com", LocalDate.of(2000, 1, 1));
        UserProfile newProfile = new UserProfile("duplicate@gmail.com", LocalDate.of(2000, 1, 1));

        List<User> users = new ArrayList<>();
        users.add(new User("duplicate@gmail.com", LocalDate.of(1999, 1, 1)));

        User result = service.updateProfile(existingUser, newProfile, users);

        assertNull(result);
    }

    @Test
    void updateProfile_success_whenEmailNotChanged() {

        User existingUser = new User("user@gmail.com", LocalDate.of(2000, 1, 1));
        UserProfile newProfile = new UserProfile("user@gmail.com", LocalDate.of(1999, 1, 1));

        List<User> users = new ArrayList<>();

        User result = service.updateProfile(existingUser, newProfile, users);

        assertNotNull(result);
    }

    @Test
    void updateProfile_success_whenUserListEmpty() {

        User existingUser = new User("user@gmail.com", LocalDate.of(2000, 1, 1));
        UserProfile newProfile = new UserProfile("new@gmail.com", LocalDate.of(2000, 1, 1));

        List<User> users = new ArrayList<>();

        User result = service.updateProfile(existingUser, newProfile, users);

        assertNotNull(result);
    }

    @Test
    void updateProfile_fail_whenDuplicateEmailAndFutureBirth() {

        User existingUser = new User("user@gmail.com", LocalDate.of(2000, 1, 1));
        UserProfile newProfile = new UserProfile("duplicate@gmail.com", LocalDate.now().plusDays(1));

        List<User> users = new ArrayList<>();
        users.add(new User("duplicate@gmail.com", LocalDate.of(1999, 1, 1)));

        User result = service.updateProfile(existingUser, newProfile, users);

        assertNull(result);
    }
}