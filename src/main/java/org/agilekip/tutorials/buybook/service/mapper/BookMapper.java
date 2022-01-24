package org.agilekip.tutorials.buybook.service.mapper;

import org.agilekip.tutorials.buybook.domain.*;
import org.agilekip.tutorials.buybook.service.dto.BookDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, PublisherMapper.class })
public interface BookMapper extends EntityMapper<BookDTO, Book> {
    @Mapping(target = "author", source = "author", qualifiedByName = "login")
    @Mapping(target = "publisher", source = "publisher", qualifiedByName = "name")
    BookDTO toDto(Book s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BookDTO toDtoName(Book book);
}
