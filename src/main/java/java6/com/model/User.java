package java6.com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Size(max = 250)
    @Nationalized
    @Column(name = "username", nullable = false, length = 250)
    @NotBlank(message = "{valid.username}")
    private String username;

    @Size(max = 50)
    @Nationalized
    @Column(name = "password", length = 50)
    @NotBlank(message = "{valid.password}")
    private String password;

    @Size(max = 50)
    @Nationalized
    @Column(name = "gmail", length = 50)
    @NotBlank(message = "{valid.gmail}")
    private String gmail;

    @Column(name = "role")
    private Boolean role;

    @Size(max = 500)
    @Nationalized
    @Column(name = "anhtaikhoan", length = 500)
    private String anhtaikhoan;

    @Column(name = "email_verified")
    private Boolean emailVerified;

}