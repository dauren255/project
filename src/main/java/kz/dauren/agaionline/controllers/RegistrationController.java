package kz.dauren.agaionline.controllers;

import kz.dauren.agaionline.models.User;
import kz.dauren.agaionline.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/myPage")
    public String blogPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userServiceImpl.findById(user.getId()));
        model.addAttribute("title", "Моя страница ");
        return "myPage";
    }

    @GetMapping("/userEdit/{id}")
    public String userEdit(@PathVariable Long id, @AuthenticationPrincipal User user, Model model) {
        if(id != user.getId()){
            return "home";
        }
        model.addAttribute("user", userServiceImpl.findById(id));
        model.addAttribute("title", "Редактирование страницы");
        return "userEditForUser";
    }

    @PostMapping("/userEdit")
    public String userEditPage(
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
//                                @RequestParam String lastname,
//                                @RequestParam String firstname,
                                @RequestParam("userId") User user,
                                Model model) {
        if(!password.equals(confirmPassword)){
            model.addAttribute("message", "Пароли не соответствуют друг другу");
            model.addAttribute("user",user);
            model.addAttribute("title", "Редактирование страницы");
            return "userEditForUser" ;
        }
        user.setUsername(username);
        user.setPassword(password);
//        user.setLastname(lastname);
//        user.setFirstname(firstname);
        userServiceImpl.save(user);
        model.addAttribute("user", user);
        return "myPage";
    }
}
