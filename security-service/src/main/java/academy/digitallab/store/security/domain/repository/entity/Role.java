package academy.digitallab.store.security.domain.repository.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "tbl_role")
public class Role {

    @Id
    private String role;
    private String status;

}
