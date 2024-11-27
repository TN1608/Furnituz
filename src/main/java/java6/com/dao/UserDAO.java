package java6.com.dao;

import java6.com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, String> {
    @Query("Select u from User u where u.gmail = ?1")
    User findByGmail(String gmail);
}
