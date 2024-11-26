package java6.com.controllers;

import jakarta.validation.Valid;
import java6.com.dao.UserDAO;
import java6.com.model.User;
import java6.com.services.CookieService;
import java6.com.services.SessionService;
import java6.com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/account")
public class LoginController extends AuthController {
    @Autowired
    SessionService session;
    @Autowired
    CookieService cookie;
    @Autowired
    UserDAO dao;
    @Autowired
    PasswordEncoder pe;
    @Autowired
    UserService uservice;

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/login/form")
    public String login(Model model,
                        @RequestParam(value = "remember",defaultValue = "false") boolean remember,
                        @RequestParam(value = "username", defaultValue = "") String username,
                        @RequestParam(value = "password", defaultValue = "") String password) throws Exception {
        uservice.loadByUsername(username);
        User user = dao.findById(username).orElse(null);
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
        if (!ConfirmPassword.equals(user.getPassword())) {
            model.addAttribute("PError", "Passwords do not match");
            return "signup";
        }
        //check user exist
        if(dao.findById(user.getUsername()).isPresent()){
            model.addAttribute("message","Tài khoản đã tồn tại");
            return "signup";
        }
        //set role
        user.setRole("USER");
        //ma hoa mat khau
        String encodedPassword = pe.encode(user.getPassword());
        user.setPassword(encodedPassword);
        dao.save(user);
        model.addAttribute("user", user);
        return "redirect:/account/login";
    }
    @GetMapping("/logout")
    public String logout(Model model){
        session.remove("user");
        cookie.remove("user");
        model.addAttribute("user",null);
        return "redirect:/home";
    }
    @GetMapping("/login/success")
    public String loginSuccess(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = dao.findById(auth.getName());
        if(user.isPresent()){
            session.set("user",user.get());
        }
        System.out.println("NAME :" + auth.getName() + "\n" + "AUTH : " + auth.getAuthorities() + "\n" + "PRINCIPAL : " + auth.getPrincipal());
        model.addAttribute("user",user);
        return "redirect:/home";
    }
    @GetMapping("/login/auth/success")
    public String loginAuthSuccess(OAuth2AuthenticationToken auth){
        uservice.loginfromOAuth2(auth);
        return "redirect:/home";
    }
}
