package org.agilekip.tutorials.buybook.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.buybook.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyBookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyBook.class);
        BuyBook buyBook1 = new BuyBook();
        buyBook1.setId(1L);
        BuyBook buyBook2 = new BuyBook();
        buyBook2.setId(buyBook1.getId());
        assertThat(buyBook1).isEqualTo(buyBook2);
        buyBook2.setId(2L);
        assertThat(buyBook1).isNotEqualTo(buyBook2);
        buyBook1.setId(null);
        assertThat(buyBook1).isNotEqualTo(buyBook2);
    }
}
