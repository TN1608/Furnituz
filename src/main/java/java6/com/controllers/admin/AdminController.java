package java6.com.controllers.admin;

import java6.com.controllers.AuthController;
import java6.com.dao.UserDAO;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController extends AuthController {
    @Autowired
    SessionService session;

    @Autowired
    UserDAO dao;

    @RequestMapping("/home")
    public String AdminHome(Model model){
        model.addAttribute("message","Hello Admin");
        return "admin/index";
    }
}
