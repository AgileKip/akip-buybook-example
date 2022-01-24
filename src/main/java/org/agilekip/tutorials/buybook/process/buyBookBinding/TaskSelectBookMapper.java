package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.agilekip.tutorials.buybook.domain.Book;
import org.agilekip.tutorials.buybook.domain.BuyBook;
import org.agilekip.tutorials.buybook.domain.BuyBookBinding;
import org.agilekip.tutorials.buybook.service.dto.BookDTO;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskSelectBookMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    BuyBookBindingDTO toBuyBookBindingDTO(BuyBookBinding buyBookBinding);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "purpose", source = "purpose")
    @Mapping(target = "book", source = "book")
    BuyBookDTO toBuyBookDTO(BuyBook buyBook);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BookDTO toBookDTO(Book name);
}
