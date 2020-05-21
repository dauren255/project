package kz.dauren.agaionline.controllers;

import io.swagger.annotations.ApiOperation;
import kz.dauren.agaionline.models.Role;
import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
//@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    @ApiOperation(  value = "Find all users",
            notes = "",
            response = User.class)
    public String userList(Model model){
        model.addAttribute("users", userServiceImpl.findAll());
        return "registration";
    }
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        if(!userServiceImpl.existsById(user.getId())){
            return "redirect:/user";
        }
        model.addAttribute("pageTitle", "Редактировать профиль");
        model.addAttribute(user);
        model.addAttribute("uroles", user.getRoles());
        model.addAttribute("roles", Role.values());
        model.addAttribute("active", user.isActive());

        return "userEdit";
    }
    @PostMapping("/registration")
    public String addUser(@AuthenticationPrincipal User currentUser, User user, Model model) {
        if(user.getPassword().length()<8){
            model.addAttribute("users", userServiceImpl.findAll());
            model.addAttribute("message", "password");
            return "registration";

        }
        if (!userServiceImpl.addUser(user)) {
            model.addAttribute("users", userServiceImpl.findAll());
            model.addAttribute("message", "account");
            return "registration";
        }

        if(currentUser == null){
            return "login";
        }
        return "redirect:/user";
    }

    @PostMapping
    public String userSave(@RequestParam String username,
//                           @RequestParam String password,
                           @RequestParam String lastname,
                           @RequestParam String firstname,
                           @RequestParam String photoLink,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user){
        user.setUsername(username);
//        user.setPassword(password);
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
        if(!form.keySet().contains("active")){
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        userServiceImpl.save(user);
        return "redirect:/user";
    }

    @PostMapping("/delete/{id}")
    public String userDelete(@PathVariable Long id){
        userServiceImpl.delete(id);
        return "redirect:/user";
    }


}
