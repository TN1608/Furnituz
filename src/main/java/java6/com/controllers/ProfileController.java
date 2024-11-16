package java6.com.controllers;

import java6.com.dao.UserDAO;
import java6.com.dao.lsttDAO;
import java6.com.model.Lichsuthanhtoan;
import java6.com.model.User;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AuthController {
    @Autowired
    UserDAO udao;
    @Autowired
    lsttDAO lsdao;
    @Autowired
    SessionService session;

    @RequestMapping("/lich-su-thanh-toan")
    public String lichSuThanhToan(Model model) {
        User user = session.get("user");
        if(user != null ){
            model.addAttribute("user",udao.findById(user.getUsername()).orElse(null));
            model.addAttribute("lstt", lsdao.findAll());
            model.addAttribute("trangthai","Đã thanh toán");
            return "profile/lichsuthanhtoan";
        }
        return "redirect:/home";
    }
}
