package java6.com.controllers;

import java6.com.dao.SanphamDAO;
import java6.com.model.CartItem;
import java6.com.model.Sanpham;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    SanphamDAO dao;
    @Autowired
    SessionService session;

    private int FIRST_PAGE_NUMBER = 0;
    private int NUMBER_OF_ITEM_PER_PAGE = 8;
    @RequestMapping("/{id}")
    public String productDetail(@PathVariable("id") String id,
                                Model model){
        Sanpham product = dao.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "chitietsanpham";
    }
    @RequestMapping("/danh-sach-san-pham/{danhmuc}")
    public String products(@PathVariable("danhmuc") String danhmuc,
                           Model model,
                           @RequestParam("p") Optional<Integer> p,
                           @RequestParam("key") Optional<String> kw,
                           @RequestParam("sort") Optional<String> sort){
        Pageable pageable;
        int currentPage = p.orElse(FIRST_PAGE_NUMBER);
        if (currentPage < FIRST_PAGE_NUMBER) {
            pageable = PageRequest.of(dao.findAll(PageRequest.of(0, NUMBER_OF_ITEM_PER_PAGE)).getTotalPages() - 1, NUMBER_OF_ITEM_PER_PAGE);
        } else if (currentPage >= dao.findAll(PageRequest.of(0, NUMBER_OF_ITEM_PER_PAGE)).getTotalPages()) {
            pageable = PageRequest.of(FIRST_PAGE_NUMBER, NUMBER_OF_ITEM_PER_PAGE);
        } else {
            pageable = PageRequest.of(currentPage, NUMBER_OF_ITEM_PER_PAGE);
        }
        if (kw.isPresent()) {
            Page<Sanpham> pages = dao.findByTensanpham(kw.get(), pageable);
            model.addAttribute("products", pages);
            model.addAttribute("key", kw.get());
            return "danhsachsanpham";
        }
        if (sort.isPresent()) {
            if (sort.get().equals("asc")) {
                pageable = PageRequest.of(currentPage, NUMBER_OF_ITEM_PER_PAGE, Sort.by("gia").ascending());
            } else if (sort.get().equals("desc")) {
                pageable = PageRequest.of(currentPage, NUMBER_OF_ITEM_PER_PAGE, Sort.by("gia").descending());
            }
        }
        Page<Sanpham> pages = dao.findByDanhmuc(danhmuc,pageable);
        model.addAttribute("products", pages);
        model.addAttribute("danhmuc", danhmuc);
        return "danhsachsanpham";
    }
}
