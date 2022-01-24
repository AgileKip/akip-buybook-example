package org.agilekip.tutorials.buybook.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BuyBook.
 */
@Entity
@Table(name = "buy_book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BuyBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "delivery_home")
    private Boolean deliveryHome;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "payment_details")
    private String paymentDetails;

    @Column(name = "purchase_review")
    private String purchaseReview;

    @ManyToOne
    @JsonIgnoreProperties(value = { "author", "publisher" }, allowSetters = true)
    private Book book;

    @ManyToOne
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuyBook id(Long id) {
        this.id = id;
        return this;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public BuyBook purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return this.description;
    }

    public BuyBook description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public BuyBook date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getDeliveryHome() {
        return this.deliveryHome;
    }

    public BuyBook deliveryHome(Boolean deliveryHome) {
        this.deliveryHome = deliveryHome;
        return this;
    }

    public void setDeliveryHome(Boolean deliveryHome) {
        this.deliveryHome = deliveryHome;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public BuyBook deliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentDetails() {
        return this.paymentDetails;
    }

    public BuyBook paymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getPurchaseReview() {
        return this.purchaseReview;
    }

    public BuyBook purchaseReview(String purchaseReview) {
        this.purchaseReview = purchaseReview;
        return this;
    }

    public void setPurchaseReview(String purchaseReview) {
        this.purchaseReview = purchaseReview;
    }

    public Book getBook() {
        return this.book;
    }

    public BuyBook book(Book book) {
        this.setBook(book);
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Store getStore() {
        return this.store;
    }

    public BuyBook store(Store store) {
        this.setStore(store);
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyBook)) {
            return false;
        }
        return id != null && id.equals(((BuyBook) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyBook{" +
            "id=" + getId() +
            ", purpose='" + getPurpose() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", deliveryHome='" + getDeliveryHome() + "'" +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", paymentDetails='" + getPaymentDetails() + "'" +
            ", purchaseReview='" + getPurchaseReview() + "'" +
            "}";
    }
}
