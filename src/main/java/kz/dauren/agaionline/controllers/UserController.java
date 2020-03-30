package kz.dauren.agaionline.controllers;

import kz.dauren.agaionline.models.Role;
import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.repo.UserRepository;
import kz.dauren.agaionline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
//@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "registration";
    }
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        if(!userService.existsById(user.getId())){
            return "redirect:/user";
        }
        model.addAttribute("pageTitle", "Редактировать профиль");
        model.addAttribute(user);
        model.addAttribute("uroles", user.getRoles());
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        if (!userService.addUser(user)) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("message", "Пользователь уже существует");
            return "registration";
        }
        return "redirect:/user";
    }

    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String lastname,
                           @RequestParam String firstname,
                           @RequestParam String photoLink,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user){
        user.setUsername(username);
        user.setPassword(password);
        user.setPhotoLink(photoLink);
        user.setLastname(lastname);
        user.setFirstname(firstname);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userService.save(user);
        return "redirect:/user";
    }


}
