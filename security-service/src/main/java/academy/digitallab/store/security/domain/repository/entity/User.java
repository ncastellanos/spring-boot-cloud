package academy.digitallab.store.security.domain.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_users")
public class User {
    @Id
    @Column(name="user_name")
    private String username;

    private String password;


    private String email;

    private Boolean enabled;

    private String status;


    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Authority> authorities;

    public Boolean isEnabled() {
        return this.enabled;
    }
}
