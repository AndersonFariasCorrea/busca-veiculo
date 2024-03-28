package com.exemple.comissao.controller;

import com.exemple.comissao.model.User;
import com.exemple.comissao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.comissao.model.User;
import com.example.comissao.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/users", method=RequestMethod.POST)
    public User createComissao(@RequestBody User user) {
        return userService.createComissao(user);
    }
}
