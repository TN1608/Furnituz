package java6.com.controllers;

import jakarta.validation.Valid;
import java6.com.dao.UserDAO;
import java6.com.model.User;
import java6.com.services.CookieService;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/account")
public class LoginController {
    @Autowired
    SessionService session;
    @Autowired
    CookieService cookie;
    @Autowired
    UserDAO dao;

    @RequestMapping("/login")
    public String login(@Valid User user,
                        BindingResult result,
                        Model model,
                        @RequestParam(value = "remember",defaultValue = "false") boolean remember,
                        @RequestParam(value = "username", defaultValue = "") String username){
        if (result.hasErrors()) {
            return "login";
        }
        User u = dao.findById(username).orElse(null);
        if (u == null) {
            model.addAttribute("message", "Username does not exist");
            return "login";
        }
        if (!u.getPassword().equals(user.getPassword())) {
            model.addAttribute("message", "Password is incorrect");
            return "login";
        }
        session.set("user", u);
        if (remember) {
            cookie.add("user", u.getUsername(),24);
        } else {
            cookie.remove("user");
        }
        model.addAttribute("user", u);
        return "redirect:/home";
    }
    @RequestMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
//        if (!user.getPassword().equals(ConfirmPassword)) {
//            model.addAttribute("PError", "Passwords do not match");
//            return "signup";
//        }
        user.setRole(false);
        dao.save(user);
        model.addAttribute("user", user);
        return "redirect:/account/login";
    }
}
