package java6.com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "sanpham")
public class Sanpham {
    @Id
    @Size(max = 20)
    @Column(name = "masp", nullable = false, length = 20)
    private String masp;

    @Size(max = 150)
    @Nationalized
    @Column(name = "tensp", length = 150)
    private String tensp;

    @Size(max = 500)
    @Nationalized
    @Column(name = "mota", length = 500)
    private String mota;

    @Size(max = 500)
    @Nationalized
    @Column(name = "anhsp", length = 500)
    private String anhsp;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madanhmuc")
    private Danhmuc madanhmuc;

}