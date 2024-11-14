package java6.com.map;

import java6.com.dao.SanphamDAO;
import java6.com.model.Sanpham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SanphamService {
    @Autowired
    SanphamDAO dao;

    public Sanpham findById(String id){
        return dao.findById(id).orElse(null);
    }
}
