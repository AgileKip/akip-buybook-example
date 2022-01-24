package org.agilekip.tutorials.buybook.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuyBookMapperTest {

    private BuyBookMapper buyBookMapper;

    @BeforeEach
    public void setUp() {
        buyBookMapper = new BuyBookMapperImpl();
    }
}
