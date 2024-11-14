package java6.com.dao;

import java6.com.model.Sanpham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanphamDAO extends JpaRepository<Sanpham, String> {
}
