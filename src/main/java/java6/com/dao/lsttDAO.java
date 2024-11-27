package java6.com.dao;

import java6.com.model.Lichsuthanhtoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface lsttDAO extends JpaRepository<Lichsuthanhtoan, String> {
}
