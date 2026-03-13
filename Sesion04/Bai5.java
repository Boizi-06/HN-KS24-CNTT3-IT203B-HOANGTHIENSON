package Sesion04;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Bai5 {

    enum Role {
        ADMIN,
        MODERATOR,
        USER
    }

    enum Action {
        DELETE_USER,
        LOCK_USER,
        VIEW_PROFILE
    }

    static class User {
        Role role;

        User(Role role) {
            this.role = role;
        }
    }

    static class AccessService {

        public boolean canPerformAction(User user, Action action) {

            if (user.role == Role.ADMIN) {
                return true;
            }

            if (user.role == Role.MODERATOR) {
                if (action == Action.DELETE_USER) {
                    return false;
                }
                return true;
            }

            if (user.role == Role.USER) {
                if (action == Action.VIEW_PROFILE) {
                    return true;
                }
                return false;
            }

            return false;
        }
    }

    AccessService service = new AccessService();
    User user;

    // ================= ADMIN TEST =================

    @Test
    void testAdminDeleteUser() {
        user = new User(Role.ADMIN);
        boolean result = service.canPerformAction(user, Action.DELETE_USER);
        assertTrue(result);
    }

    @Test
    void testAdminLockUser() {
        user = new User(Role.ADMIN);
        boolean result = service.canPerformAction(user, Action.LOCK_USER);
        assertTrue(result);
    }

    @Test
    void testAdminViewProfile() {
        user = new User(Role.ADMIN);
        boolean result = service.canPerformAction(user, Action.VIEW_PROFILE);
        assertTrue(result);
    }

    // ================= MODERATOR TEST =================

    @Test
    void testModeratorDeleteUser() {
        user = new User(Role.MODERATOR);
        boolean result = service.canPerformAction(user, Action.DELETE_USER);
        assertFalse(result);
    }

    @Test
    void testModeratorLockUser() {
        user = new User(Role.MODERATOR);
        boolean result = service.canPerformAction(user, Action.LOCK_USER);
        assertTrue(result);
    }

    @Test
    void testModeratorViewProfile() {
        user = new User(Role.MODERATOR);
        boolean result = service.canPerformAction(user, Action.VIEW_PROFILE);
        assertTrue(result);
    }

    // ================= USER TEST =================

    @Test
    void testUserDeleteUser() {
        user = new User(Role.USER);
        boolean result = service.canPerformAction(user, Action.DELETE_USER);
        assertFalse(result);
    }

    @Test
    void testUserLockUser() {
        user = new User(Role.USER);
        boolean result = service.canPerformAction(user, Action.LOCK_USER);
        assertFalse(result);
    }

    @Test
    void testUserViewProfile() {
        user = new User(Role.USER);
        boolean result = service.canPerformAction(user, Action.VIEW_PROFILE);
        assertTrue(result);
    }

    // dọn dẹp sau mỗi test
    @AfterEach
    void cleanup() {
        user = null;
    }
}