package org.agilekip.tutorials.buybook.service.mapper;

import org.agilekip.tutorials.buybook.domain.*;
import org.agilekip.tutorials.buybook.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StoreDTO toDtoName(Store store);
}
