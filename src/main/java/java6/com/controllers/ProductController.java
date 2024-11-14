package java6.com.controllers;

import java6.com.dao.SanphamDAO;
import java6.com.model.CartItem;
import java6.com.model.Sanpham;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    SanphamDAO dao;
    @Autowired
    SessionService session;

    @RequestMapping("/{id}")
    public String productDetail(@PathVariable("id") String id,
                                Model model){
        Sanpham product = dao.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "chitietsanpham";
    }
}
