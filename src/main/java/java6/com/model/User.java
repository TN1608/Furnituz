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
@Table(name = "users")
public class User {
    @Id
    @Size(max = 50)
    @Nationalized
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "pass", length = 50)
    private String pass;

    @Size(max = 50)
    @Nationalized
    @Column(name = "fullname", length = 50)
    private String fullname;

    @Size(max = 50)
    @Nationalized
    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "role")
    private Boolean role;

}