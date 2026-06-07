package library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import library.model.User;

public class UserService {
    private final Map<String, User> users = new LinkedHashMap<>();

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public User findById(String id) {
        return users.get(id);
    }

    public Collection<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void printUsers() {
        System.out.println("\n[사용자 목록]");
        for (User user : users.values()) {
            System.out.println("- " + user);
        }
    }
}

