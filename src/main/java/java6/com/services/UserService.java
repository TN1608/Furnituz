package java6.com.services;

import java6.com.dao.UserDAO;
import java6.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

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

    public UserDetails loadByUsername(String username) throws Exception {
        User user = dao.findById(username).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
    public void loginfromOAuth2(OAuth2AuthenticationToken oauth){
//        String username = oauth.getPrincipal().getAttribute("name");
        String email = oauth.getPrincipal().getAttribute("email");
        String password = Long.toHexString(System.currentTimeMillis());
        User user = dao.findById(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(email);
            newUser.setPassword(pe.encode("defaultPassword")); // Mật khẩu mặc định
            newUser.setRole("USER");
            dao.save(newUser);
            return newUser;
        });

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        session.set("user", user); // Không lưu UserDetails vào session
    }
}
