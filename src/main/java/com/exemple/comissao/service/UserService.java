package com.exemple.comissao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comissao.model.User;
import com.example.comissao.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // CREATE
    public User createUser(User emp) {
        return userRepository.save(emp);
    }

    // READ
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // DELETE
    public void deleteUser(Long userid) {
        userRepository.deleteById(userid);
    }

    // UPDATE
    public User updateUser(Long userid, User userDetails) {
        User emp = empRepository.findById(useid).get();
        emp.setFirstName(userDetails.getFirstName());
        emp.setLastName(userDetails.getLastName());
        emp.setEmailId(userDetails.getEmailId());

        return userRepository.save(emp);
    }
}