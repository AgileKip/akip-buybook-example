package org.agilekip.tutorials.buybook.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.buybook.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyBookDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyBookDTO.class);
        BuyBookDTO buyBookDTO1 = new BuyBookDTO();
        buyBookDTO1.setId(1L);
        BuyBookDTO buyBookDTO2 = new BuyBookDTO();
        assertThat(buyBookDTO1).isNotEqualTo(buyBookDTO2);
        buyBookDTO2.setId(buyBookDTO1.getId());
        assertThat(buyBookDTO1).isEqualTo(buyBookDTO2);
        buyBookDTO2.setId(2L);
        assertThat(buyBookDTO1).isNotEqualTo(buyBookDTO2);
        buyBookDTO1.setId(null);
        assertThat(buyBookDTO1).isNotEqualTo(buyBookDTO2);
    }
}
