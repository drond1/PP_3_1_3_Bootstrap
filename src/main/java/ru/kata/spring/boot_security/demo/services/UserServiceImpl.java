package ru.kata.spring.boot_security.demo.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Role> findRolesByName(String roleName) {
        List<Role> roles = new ArrayList<>();
        for (Role role : getAllRoles()) {
            if (roleName.contains(role.getName()))
                roles.add(role);
        }
        return roles;
    }

    public User showOneUser(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateUser(User user) {
        User userToUpdate = getUserById(user.getId());
        if (!userToUpdate.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        return user;
    }


}
