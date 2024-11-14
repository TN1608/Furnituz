package java6.com.controllers;

import java6.com.dao.SanphamDAO;
import java6.com.model.Sanpham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    SanphamDAO dao;
    @RequestMapping({"/", "/home"})
    public String home(Model model) {
        List<Sanpham> list = new ArrayList<>();
        dao.findAll().forEach(list::add);
//        list.forEach(product -> System.out.println(product.getTensp() + " - " + product.getMadanhmuc().getTendm().equals("GHẾ")));
        model.addAttribute("Sanpham", list);
        return "index";
    }
}
