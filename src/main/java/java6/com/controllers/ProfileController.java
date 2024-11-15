package java6.com.controllers;

import java6.com.dao.UserDAO;
import java6.com.dao.lsttDAO;
import java6.com.model.Lichsuthanhtoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserDAO udao;
    @Autowired
    lsttDAO lsdao;

    @RequestMapping("/lich-su-thanh-toan")
    public String lichSuThanhToan(Model model) {
        model.addAttribute("lstt", lsdao.findAll());
        model.addAttribute("trangthai","Đã thanh toán");
        return "profile/lichsuthanhtoan";
    }
}
