package org.agilekip.tutorials.buybook.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.buybook.domain.BuyBook} entity.
 */
public class BuyBookDTO implements Serializable {

    private Long id;

    @NotNull
    private String purpose;

    private String description;

    private LocalDate date;

    private Boolean deliveryHome;

    private String deliveryAddress;

    private String paymentDetails;

    private String purchaseReview;

    private BookDTO book;

    private StoreDTO store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getDeliveryHome() {
        return deliveryHome;
    }

    public void setDeliveryHome(Boolean deliveryHome) {
        this.deliveryHome = deliveryHome;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getPurchaseReview() {
        return purchaseReview;
    }

    public void setPurchaseReview(String purchaseReview) {
        this.purchaseReview = purchaseReview;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyBookDTO)) {
            return false;
        }

        BuyBookDTO buyBookDTO = (BuyBookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buyBookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyBookDTO{" +
            "id=" + getId() +
            ", purpose='" + getPurpose() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", deliveryHome='" + getDeliveryHome() + "'" +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", paymentDetails='" + getPaymentDetails() + "'" +
            ", purchaseReview='" + getPurchaseReview() + "'" +
            ", book=" + getBook() +
            ", store=" + getStore() +
            "}";
    }
}
