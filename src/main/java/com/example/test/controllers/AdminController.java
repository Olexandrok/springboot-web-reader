package com.example.test.controllers;

import com.example.test.entities.Role;
import com.example.test.entities.User;
import com.example.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String application(Model model, Principal principal){
        User user = userService.loadByUserId(userService.loadUserByUsername(principal.getName()).getId());
        if (user.getRole().equals(Role.ADMIN)) {
            model.addAttribute("users", userService.findAll());
            return "adminPage";
        } else
            return "redirect:/";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("box") List<String> ids, @RequestParam("action") String action){
        switch (action){
            case "delete":
                for(String id:ids){
                    userService.deleteById(Long.parseLong(id));
                }
                break;
            case "block" :
                for (String id : ids) {
                    User userFromDB = userService.getById(Long.parseLong(id));
                    userFromDB.setEnabled(false);
                    userService.save(userFromDB);
                }
                break;
            case "unblock" :
                for (String id : ids) {
                    User userFromDB = userService.getById(Long.parseLong(id));
                    userFromDB.setEnabled(true);
                    userService.save(userFromDB);
                }
                break;
            case "admin" :
                for (String id: ids) {
                    User userFromDb = userService.getById(Long.parseLong(id));
                    userFromDb.setRole(Role.ADMIN);
                    userService.save(userFromDb);
                }
                break;
            case "unadmin" :
                for (String id: ids) {
                    User userFromDb = userService.getById(Long.parseLong(id));
                    userFromDb.setRole(Role.USER);
                    userService.save(userFromDb);
                }
        }

        return "redirect:/admin";
    }

}
