package java6.com.controllers;

import java6.com.dao.SanphamDAO;
import java6.com.dao.UserDAO;
import java6.com.model.Sanpham;
import java6.com.model.User;
import java6.com.services.SessionService;
import java6.com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    SanphamDAO dao;
    @Autowired
    UserDAO udao;
    @Autowired
    SessionService session;
    @Autowired
    UserService userService;

    private int FIRST_PAGE_NUMBER = 0;
    private int NUMBER_OF_ITEM_PER_PAGE = 8;
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping({"/", "/home"})
    public String home(Model model,
                       @RequestParam("sort") Optional<String> sort,
                       @RequestParam("p") Optional<Integer> page){
        User u = session.get("user");
        if(u != null){
            model.addAttribute("user", udao.findById(u.getUsername()).orElse(null));
        }
        int currentPage = page.orElse(FIRST_PAGE_NUMBER);
        Pageable pageable;
        if (currentPage < FIRST_PAGE_NUMBER) {
            pageable = PageRequest.of(dao.findAll(PageRequest.of(0, NUMBER_OF_ITEM_PER_PAGE)).getTotalPages() - 1, NUMBER_OF_ITEM_PER_PAGE);
        } else if (currentPage >= dao.findAll(PageRequest.of(0, NUMBER_OF_ITEM_PER_PAGE)).getTotalPages()) {
            pageable = PageRequest.of(FIRST_PAGE_NUMBER, NUMBER_OF_ITEM_PER_PAGE);
        } else {
            pageable = PageRequest.of(currentPage, NUMBER_OF_ITEM_PER_PAGE);
        }

        if (sort.isPresent()) {
            if (sort.get().equals("asc")) {
                pageable = PageRequest.of(currentPage, NUMBER_OF_ITEM_PER_PAGE, Sort.by("gia").ascending());
            } else if (sort.get().equals("desc")) {
                pageable = PageRequest.of(currentPage, NUMBER_OF_ITEM_PER_PAGE, Sort.by("gia").descending());
            }
        }
        Page<Sanpham> pages = dao.findAll(pageable);
        model.addAttribute("Sanpham", pages.getContent());
        model.addAttribute("currentPage", pages.getNumber()); // Current page number
        model.addAttribute("totalPages", pages.getTotalPages()); // Total pages
        model.addAttribute("sort", sort.orElse("")); // Current sort order
        return "index";
    }
}
