package java6.com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "lichsuthanhtoan")
public class Lichsuthanhtoan {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "sotien")
    private Double sotien;

    @Column(name = "ngaythanhtoan")
    private LocalDate ngaythanhtoan;

    @Size(max = 120)
    @Nationalized
    @Column(name = "hinhthucthanhtoan", length = 120)
    private String hinhthucthanhtoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User username;

}