package java6.com.controllers;

import java6.com.dao.UserDAO;
import java6.com.model.User;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public abstract class AuthController {
    @Autowired
    SessionService session;
    @Autowired
    UserDAO dao;

    @ModelAttribute
    public void getUser(Model model) {
        User user = session.get("user");
        if(user != null){
            model.addAttribute("user",dao.findById(user.getUsername()).orElse(null));
        }
    }
}
