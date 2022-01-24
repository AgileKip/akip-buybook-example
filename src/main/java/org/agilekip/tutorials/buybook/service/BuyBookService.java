package org.agilekip.tutorials.buybook.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.buybook.domain.BuyBook;
import org.agilekip.tutorials.buybook.repository.BuyBookRepository;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.agilekip.tutorials.buybook.service.mapper.BuyBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuyBook}.
 */
@Service
@Transactional
public class BuyBookService {

    private final Logger log = LoggerFactory.getLogger(BuyBookService.class);

    private final BuyBookRepository buyBookRepository;

    private final BuyBookMapper buyBookMapper;

    public BuyBookService(BuyBookRepository buyBookRepository, BuyBookMapper buyBookMapper) {
        this.buyBookRepository = buyBookRepository;
        this.buyBookMapper = buyBookMapper;
    }

    /**
     * Save a buyBook.
     *
     * @param buyBookDTO the entity to save.
     * @return the persisted entity.
     */
    public BuyBookDTO save(BuyBookDTO buyBookDTO) {
        log.debug("Request to save BuyBook : {}", buyBookDTO);
        BuyBook buyBook = buyBookMapper.toEntity(buyBookDTO);
        buyBook = buyBookRepository.save(buyBook);
        return buyBookMapper.toDto(buyBook);
    }

    /**
     * Partially update a buyBook.
     *
     * @param buyBookDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BuyBookDTO> partialUpdate(BuyBookDTO buyBookDTO) {
        log.debug("Request to partially update BuyBook : {}", buyBookDTO);

        return buyBookRepository
            .findById(buyBookDTO.getId())
            .map(
                existingBuyBook -> {
                    buyBookMapper.partialUpdate(existingBuyBook, buyBookDTO);
                    return existingBuyBook;
                }
            )
            .map(buyBookRepository::save)
            .map(buyBookMapper::toDto);
    }

    /**
     * Get all the buyBooks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BuyBookDTO> findAll() {
        log.debug("Request to get all BuyBooks");
        return buyBookRepository.findAll().stream().map(buyBookMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one buyBook by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BuyBookDTO> findOne(Long id) {
        log.debug("Request to get BuyBook : {}", id);
        return buyBookRepository.findById(id).map(buyBookMapper::toDto);
    }

    /**
     * Delete the buyBook by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BuyBook : {}", id);
        buyBookRepository.deleteById(id);
    }
}
