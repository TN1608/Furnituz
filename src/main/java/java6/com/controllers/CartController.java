package java6.com.controllers;

import jakarta.servlet.http.HttpServletRequest;
import java6.com.dao.SanphamDAO;
import java6.com.dao.lsttDAO;
import java6.com.model.CartItem;
import java6.com.model.Lichsuthanhtoan;
import java6.com.model.Sanpham;
import java6.com.services.SessionService;
import java6.com.services.VNPAYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController extends AuthController {
    @Autowired
    SessionService session;
    @Autowired
    SanphamDAO dao;
    @Autowired
    lsttDAO lsDAO;
    @Autowired
    VNPAYService vnPayService;
    @RequestMapping("/cart")
    public String cart(Model model){
        List<CartItem> cart = session.get("Cart");
        if(cart == null){
            cart = new ArrayList<>();
        }

        double total = 0;
        for(CartItem item : cart){
            total += item.getQuantity() * item.getPrice();
        }
        System.out.println("CART ITEM : "+ cart);
        model.addAttribute("cart", session.get("Cart"));
        model.addAttribute("total", total);
        return "cart";
    }
    @RequestMapping("/product/addtocart")
    public String addtoCart(@RequestParam("id") String id,Model model){
        Sanpham product = dao.findById(id).orElse(null);
        boolean found = false;
        List<CartItem> list = session.get("Cart");
        if(list == null){
            list = new ArrayList<>();
        }
        for(CartItem item : list){
            if(item.getProduct().getMasp().equals(id)){
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if(!found){
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(1);
            item.setPrice(product.getGia());
            list.add(item);
        }

        session.set("Cart", list);
        model.addAttribute("cart", list);

        System.out.println("Add to cart: " + id);
        return "redirect:/cart";
    }

    @RequestMapping("/cart/remove")
    public String remove(@RequestParam("id") String id, Model model){
        List<CartItem> list = session.get("Cart");
        if(list != null){
            for(CartItem item : list){
                if(item.getProduct().getMasp().equals(id)){
                    list.remove(item);
                    break;
                }
            }
        }
        session.set("Cart", list);
        model.addAttribute("cart", list);
        return "redirect:/cart";
    }

    //CHECK OUT VNPAY
    @GetMapping("/submitOrder")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) {
        if(orderTotal <= 0) {
            return "redirect:/cart";
        }
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
//        END VNPAY CODE

        double sotienthucte = Double.parseDouble(totalPrice) / 100;
        // luu len LSTT
        Lichsuthanhtoan payment = new Lichsuthanhtoan();
        payment.setId(Integer.parseInt(transactionId));
        payment.setSotien(sotienthucte);
        payment.setNgaythanhtoan(LocalDate.parse(paymentTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        payment.setHinhthucthanhtoan("VNPAY");
        lsDAO.save(payment);
        return "redirect:/home";
    }
}
