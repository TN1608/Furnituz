package java6.com.services;

import java6.com.dao.UserDAO;
import java6.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder pe;
    @Autowired
    private SessionService session;
    @Autowired
    private CookieService cookie;
    @Autowired
    private UserDAO dao;

    public void register(String username, String password) {
        String encodedPassword = pe.encode(password);
        System.out.println("Username: " + username);
        System.out.println("Encoded password: " + encodedPassword);
    }
    private void getCurrentUser(Model model) {
        User user = session.get("user");
        if (user != null) {
            model.addAttribute("user", dao.findById(user.getUsername()).orElse(null));
        }
    }
}
