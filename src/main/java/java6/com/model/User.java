package java6.com.model;

import jakarta.persistence.*;
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
    @Size(max = 250)
    @Nationalized
    @Column(name = "username", nullable = false, length = 250)
    private String username;

    @Nationalized
    @Lob
    @Column(name = "password")
    private String password;

    @Size(max = 50)
    @Nationalized
    @Column(name = "gmail", length = 50)
    private String gmail;

    @Size(max = 20)
    @Nationalized
    @Column(name = "role", length = 20)
    private String role;

    @Size(max = 500)
    @Nationalized
    @Column(name = "anhtaikhoan", length = 500)
    private String anhtaikhoan;

    @Column(name = "email_verified")
    private Boolean emailVerified;

}