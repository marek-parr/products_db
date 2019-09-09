package users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    private boolean active;
    @NotBlank
    private String role;

    public User() {
    }

    public User(@NotBlank String username, @NotBlank String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

}
