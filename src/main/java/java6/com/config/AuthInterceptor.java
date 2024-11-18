package java6.com.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java6.com.dao.UserDAO;
import java6.com.model.User;
import java6.com.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    SessionService session;

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy user từ session
        User user = session.get("user");
        if (user != null) {
            // Đảm bảo user tồn tại trong database (trường hợp bị xóa)
            if(request.getAttribute("user") == null){
                request.setAttribute("user", userDAO.findById(user.getUsername()).orElse(null));
            }
        }
        return true; // Tiếp tục xử lý request
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            // Thêm currentUser vào model
            if(!modelAndView.getModel().containsKey("user")){
                Object currentUser = request.getAttribute("user");
                if (currentUser != null) {
                    modelAndView.addObject("user", currentUser);
                }
            }
        }
    }

    public boolean isAdmin(User user){
        return user != null && "ADMIN".equals(user.getRole());
    }

    public boolean isUser(User user){
        return user != null && "USER".equals(user.getRole());
    }
}
