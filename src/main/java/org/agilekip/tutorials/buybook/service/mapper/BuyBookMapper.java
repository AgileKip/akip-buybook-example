package org.agilekip.tutorials.buybook.service.mapper;

import org.agilekip.tutorials.buybook.domain.*;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuyBook} and its DTO {@link BuyBookDTO}.
 */
@Mapper(componentModel = "spring", uses = { BookMapper.class, StoreMapper.class })
public interface BuyBookMapper extends EntityMapper<BuyBookDTO, BuyBook> {
    @Mapping(target = "book", source = "book", qualifiedByName = "name")
    @Mapping(target = "store", source = "store", qualifiedByName = "name")
    BuyBookDTO toDto(BuyBook s);
}
