package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.agilekip.tutorials.buybook.domain.BuyBook;
import org.agilekip.tutorials.buybook.domain.BuyBookBinding;
import org.agilekip.tutorials.buybook.domain.Store;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.agilekip.tutorials.buybook.service.dto.StoreDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskSelectPickUpStoreMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    BuyBookBindingDTO toBuyBookBindingDTO(BuyBookBinding buyBookBinding);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "purpose", source = "purpose")
    @Mapping(target = "store", source = "store")
    BuyBookDTO toBuyBookDTO(BuyBook buyBook);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StoreDTO toStoreDTO(Store name);
}
