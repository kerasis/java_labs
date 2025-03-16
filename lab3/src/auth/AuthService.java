package auth;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users;

    public AuthService() {
        users = new HashMap<>();
        // Добавляем тестовых пользователей
        users.put("admin", new User("admin", "admin123", true));
        users.put("user", new User("user", "user123", false));
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public static class User {
        private String username;
        private String password;
        private boolean isAdmin;

        public User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public boolean isAdmin() {
            return isAdmin;
        }
    }
} 