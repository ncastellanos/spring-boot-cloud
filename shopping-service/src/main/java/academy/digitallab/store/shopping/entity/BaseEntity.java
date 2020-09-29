package academy.digitallab.store.shopping.entity;


import java.util.Date;

public interface BaseEntity {
    public String getCreatedBy();

    public void setCreatedBy(String createdBy);

    public Date getCreatedDate() ;

    public void setCreatedDate(Date createdDate);

    public String getUpdatedBy();

    public void setUpdatedBy(String updatedBy);

    public Date getUpdatedDate();

    public void setUpdatedDate(Date updatedDate);

    public Long getRowVersion() ;

    public void setRowVersion(Long rowVersion) ;
}
