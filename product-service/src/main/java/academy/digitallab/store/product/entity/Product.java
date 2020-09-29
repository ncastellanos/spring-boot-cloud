package academy.digitallab.store.product.entity;

import academy.digitallab.store.product.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_products")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Product extends AuditingEntity implements Serializable,BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "numero producto no debe ser vacío")
    @Column(name = "number_product", unique = true)
    private String numberProduct;

    @NotEmpty(message = "El nombre no debe ser vacío")
    private String name;
    private String description;
    @Positive(message = "El stock debe ser mayor que cero")
    private Double stock;
    private Double price;
    private String status;



    @NotNull(message = "La categoria no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Category category;

    @PrePersist
    public void prePersist() {
        this.setCreatedDate(new Date());
    }
    @PreUpdate
    public void preUpdate() {
        this.setUpdatedDate(new Date());
    }

}
