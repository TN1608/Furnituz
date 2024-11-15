package java6.com.controllers;

import jakarta.validation.Valid;
import java6.com.dao.UserDAO;
import java6.com.model.User;
import java6.com.services.CookieService;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder pe;
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "remember",defaultValue = "false") boolean remember,
                        @RequestParam(value = "username", defaultValue = "") String username,
                        @RequestParam(value = "password", defaultValue = "") String password) {
        User user= dao.findById(username).orElse(null);
        if(username.isEmpty() || password.isEmpty()){
            model.addAttribute("message","Mật khẩu hoặc tài khoản không đúng");
            return "login";
        }

        //check encode pass
        if(user == null){
            model.addAttribute("message","Tài khoản không tồn tại");
            return "login";
        }
        if(!pe.matches(password,user.getPassword())){
            model.addAttribute("message","Mật khẩu sai!");
            return "login";
        }
        session.set("user",user);
        if(remember){
            cookie.add("user",user.getUsername(),24);
        }else{
            cookie.remove("user");
        }
        model.addAttribute("user",user);
        return "redirect:/home";
    }
    @RequestMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user,
                         BindingResult result,
                         Model model,
                         @RequestParam(value = "cpass", defaultValue = "") String ConfirmPassword) {
        if (result.hasErrors()) {
            return "signup";
        }
        if (!user.getPassword().equals(ConfirmPassword)) {
            model.addAttribute("PError", "Passwords do not match");
            return "signup";
        }
        user.setRole(false);
        //ma hoa mat khau
        String encodedPassword = pe.encode(user.getPassword());
        user.setPassword(encodedPassword);
        dao.save(user);
        model.addAttribute("user", user);
        return "redirect:/account/login";
    }
    @RequestMapping("/logout")
    public String logout(Model model){
        session.remove("user");
        cookie.remove("user");
        model.addAttribute("user",null);
        return "redirect:/home";
    }
}
