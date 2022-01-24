package org.agilekip.tutorials.buybook.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.buybook.domain.Publisher;
import org.agilekip.tutorials.buybook.repository.PublisherRepository;
import org.agilekip.tutorials.buybook.service.dto.PublisherDTO;
import org.agilekip.tutorials.buybook.service.mapper.PublisherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Publisher}.
 */
@Service
@Transactional
public class PublisherService {

    private final Logger log = LoggerFactory.getLogger(PublisherService.class);

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;

    public PublisherService(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    /**
     * Save a publisher.
     *
     * @param publisherDTO the entity to save.
     * @return the persisted entity.
     */
    public PublisherDTO save(PublisherDTO publisherDTO) {
        log.debug("Request to save Publisher : {}", publisherDTO);
        Publisher publisher = publisherMapper.toEntity(publisherDTO);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(publisher);
    }

    /**
     * Partially update a publisher.
     *
     * @param publisherDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PublisherDTO> partialUpdate(PublisherDTO publisherDTO) {
        log.debug("Request to partially update Publisher : {}", publisherDTO);

        return publisherRepository
            .findById(publisherDTO.getId())
            .map(
                existingPublisher -> {
                    publisherMapper.partialUpdate(existingPublisher, publisherDTO);
                    return existingPublisher;
                }
            )
            .map(publisherRepository::save)
            .map(publisherMapper::toDto);
    }

    /**
     * Get all the publishers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PublisherDTO> findAll() {
        log.debug("Request to get all Publishers");
        return publisherRepository.findAll().stream().map(publisherMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one publisher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PublisherDTO> findOne(Long id) {
        log.debug("Request to get Publisher : {}", id);
        return publisherRepository.findById(id).map(publisherMapper::toDto);
    }

    /**
     * Delete the publisher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Publisher : {}", id);
        publisherRepository.deleteById(id);
    }
}
