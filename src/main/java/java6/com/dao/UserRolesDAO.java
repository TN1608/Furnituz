package java6.com.dao;

import java6.com.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRolesDAO extends JpaRepository<UserRole, Integer> {
}
