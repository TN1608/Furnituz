package java6.com.controllers;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import java6.com.dao.UserDAO;
import java6.com.model.User;
import java6.com.services.CookieService;
import java6.com.services.EmailSenderService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Random;


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
    @Autowired
    private EmailSenderService mailService;

    String otp;

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

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
//        cookie.remove("user");
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
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("showOtp",false);
        model.addAttribute("showResetForm",false);
        return "forgotPassword";
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String cpassword,
                                Model model){

        model.addAttribute("email",session.get("email"));
        User user = dao.findByGmail(email);
        if(!password.equals(cpassword)){
            model.addAttribute("message","Mật khẩu không khớp");
            model.addAttribute("showOtp",false);
            model.addAttribute("showResetForm",true);
            return "forgotPassword";
        }
        if(pe.matches(password,user.getPassword())){
            model.addAttribute("message","Mật khẩu mới không được trùng với mật khẩu cũ");
            model.addAttribute("showOtp",false);
            model.addAttribute("showResetForm",true);
            return "forgotPassword";
        }
        user.setPassword(pe.encode(password));
        dao.save(user);
        model.addAttribute("message","Đổi mật khẩu thành công");
        return "redirect:/account/login";
    }
    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email,
                          Model model,
                          RedirectAttributes ra) throws MessagingException {
        User user = dao.findByGmail(email);
        if(user == null){
            model.addAttribute("message","Email không tồn tại");
            model.addAttribute("showOtp",false);
            model.addAttribute("showResetForm",false);
            return "forgotPassword";
        }
        //send otp
        otp = generateOTP();
        String emailContent = "<h2>Xác nhận email</h2>"
                + "<p>Xin chào " + user.getUsername() + ",</p>"
                + "<p>Vui lòng sử dụng mã xác nhận bên dưới để hoàn tất quên mật khẩu:</p>"
                + "<h3 style='color:blue;'>" + otp + "</h3>"
                + "<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>";

        // Send email
        mailService.sendEmail(email, otp + " là mã xác thực của bạn. ", emailContent);
        model.addAttribute("message","Mã OTP đã được gửi đến email của bạn");
        model.addAttribute("showOtp",true);
        model.addAttribute("showResetForm",false);
        session.set("email",email);
        model.addAttribute("email",email);
        return "forgotPassword";
    }
    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam("otp") String inputOTP,
                            Model model,
                            RedirectAttributes ra){
        model.addAttribute("email",session.get("email"));
        if(inputOTP.equals(otp)){
            model.addAttribute("showResetForm",true);
            model.addAttribute("showOtp",false);
            return "forgotPassword";
        }
        model.addAttribute("message","Mã OTP không đúng");
        model.addAttribute("showOtp",true);
        model.addAttribute("showResetForm",false);
        return "forgotPassword";
    }
}
