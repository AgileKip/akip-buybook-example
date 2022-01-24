package org.agilekip.tutorials.buybook.service.mapper;

import org.agilekip.tutorials.buybook.domain.*;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuyBookBinding} and its DTO {@link BuyBookBindingDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, BuyBookMapper.class })
public interface BuyBookBindingMapper extends EntityMapper<BuyBookBindingDTO, BuyBookBinding> {
    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "buyBook", source = "buyBook")
    BuyBookBindingDTO toDto(BuyBookBinding s);
}
