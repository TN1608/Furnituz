package java6.com.dao;

import java6.com.model.Sanpham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SanphamDAO extends JpaRepository<Sanpham, String> {
    @Query("Select s from Sanpham s where s.madanhmuc.madm = ?1")
    public Page<Sanpham> findByDanhmuc(String danhmuc, Pageable pageable);
    @Query("select s from Sanpham s where s.tensp like %?1%")
    public Page<Sanpham> findByKeywords(String keyword,Pageable pageable);
    @Query("select s from Sanpham s where s.gia between ?1 and ?2")
    public Page<Sanpham> findByPriceRange(double min, double max,Pageable pageable);
}
