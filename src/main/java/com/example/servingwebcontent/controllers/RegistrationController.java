package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.domain.Role;
import com.example.servingwebcontent.domain.User;
import com.example.servingwebcontent.service.RoleService;
import com.example.servingwebcontent.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }



    @GetMapping("/registration")
    public String registration () {
        return ("registration");
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {


        //Поиск юзеров на наличие в БД
        User userFromDb = userService.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Введенный вами пользователь уже существует!");
            return "registration";
        }


        //Запись строки Enabled в таблицу users
        user.setEnabled(true);

        //кодировка пароля
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);


        //Установка роли USER для нового пользователя
        Role role = new Role (user.getUsername(), "ROLE_USER");
        roleService.saveRole(role);


        return ("redirect:/login");
    }
}
