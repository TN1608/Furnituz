package java6.com.controllers;

import java6.com.model.Sanpham;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @RequestMapping({"/", "/home"})
    public String home(Model model) {
        List<Sanpham> products = new ArrayList<>();


        return "index";
    }
}
