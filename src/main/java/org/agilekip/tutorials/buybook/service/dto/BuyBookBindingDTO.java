package org.agilekip.tutorials.buybook.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.akip.service.dto.ProcessInstanceDTO;

/**
 * A DTO for the {@link org.agilekip.tutorials.buybook.domain.BuyBookBinding} entity.
 */
public class BuyBookBindingDTO implements Serializable {

    private Long id;

    private ProcessInstanceDTO processInstance;

    private BuyBookDTO buyBook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessInstanceDTO getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceDTO processInstance) {
        this.processInstance = processInstance;
    }

    public BuyBookDTO getBuyBook() {
        return buyBook;
    }

    public void setBuyBook(BuyBookDTO buyBook) {
        this.buyBook = buyBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyBookBindingDTO)) {
            return false;
        }

        BuyBookBindingDTO buyBookBindingDTO = (BuyBookBindingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buyBookBindingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyBookBindingDTO{" +
            "id=" + getId() +
            ", processInstance=" + getProcessInstance() +
            ", buyBook=" + getBuyBook() +
            "}";
    }
}
