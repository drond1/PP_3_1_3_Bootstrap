package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> showAllUsers();

    List<Role> getAllRoles();

    List<Role> findRolesByName(String roleName);

    User showOneUser(int id);

    User findByUsername(String username);

    void saveUser(User user);

    User getUserById(int id);

    void updateUser(User user);

    void deleteUser(int id);
}
