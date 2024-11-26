package java6.com.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Size(max = 20)
    @Nationalized
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Size(max = 20)
    @Nationalized
    @Column(name = "name", length = 20)
    private String name;

    @OneToMany(mappedBy = "roleid")
    private Set<java6.com.model.UserRole> userRoles = new LinkedHashSet<>();

}