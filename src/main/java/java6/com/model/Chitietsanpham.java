package java6.com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "chitietsanpham")
public class Chitietsanpham {
    @Id
    @Size(max = 20)
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Size(max = 500)
    @Nationalized
    @Column(name = "anhchitiet", length = 500)
    private String anhchitiet;

    @Nationalized
    @Lob
    @Column(name = "noidung")
    private String noidung;

}