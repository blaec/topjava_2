package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("user", "user@google.com", "user", Role.ROLE_USER),
            new User("admin", "admin@google.com", "admin", Role.ROLE_ADMIN)
    );


    public static void main(String[] args) {

        // testing InMemoryUserRepositoryImpl
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();

        System.out.println(userRepository.get(2));
        System.out.println(userRepository.getByEmail("admin@google.com"));
        System.out.println(userRepository.save(new User("test", "test@gmail.com", "test", Role.ROLE_USER)));
        System.out.println(userRepository.get(3));
        System.out.println(userRepository.delete(3));
        System.out.println(userRepository.get(3));
        System.out.println(userRepository.getByEmail("test@gmail.com"));
        System.out.println(userRepository.getAll());
    }
}
