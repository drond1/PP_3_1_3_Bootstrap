package ru.kata.spring.boot_security.demo.services;

import com.kata.springboot.pp_3_1_1.dao.UserDao;
import com.kata.springboot.pp_3_1_1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDAO;

    @Autowired
    public UserServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> showAllUsers() {
        return userDAO.showAllUsers();
    }
    @Override
    public User show(int id) {
        return userDAO.showUser(id);
    }
    @Transactional
    @Override
    public void save(User user) {
        userDAO.saveUser(user);
    }
    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        userDAO.updateUser(id, updatedUser);
    }
    @Transactional
    @Override
    public void delete(int id) {
        userDAO.deleteUser(id);
    }
}
