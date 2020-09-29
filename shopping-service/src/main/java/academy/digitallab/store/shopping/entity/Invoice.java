package academy.digitallab.store.shopping.entity;

import academy.digitallab.store.shopping.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tbl_invoices")
public class Invoice extends AuditingEntity implements Serializable , BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El número de documento no debe vacío")
    @Column(name = "number_invoice")
    private String numberInvoice;

    private String description;

    @NotNull(message = "El cliente no  debe ser vacío")
    @Column(name = "customer_id")
    private Long customerId;


    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;

    private String state;

    @Transient
    private Customer customer;

    public Invoice(){
        items = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.setCreatedDate(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedDate(new Date());
    }

}
