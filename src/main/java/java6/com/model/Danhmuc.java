package java6.com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "danhmuc")
public class Danhmuc {
    @Id
    @Size(max = 200)
    @Nationalized
    @Column(name = "madm", nullable = false, length = 200)
    private String madm;

    @Size(max = 200)
    @Nationalized
    @Column(name = "tendm", length = 200)
    private String tendm;

}