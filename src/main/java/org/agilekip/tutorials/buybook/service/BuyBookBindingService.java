package org.agilekip.tutorials.buybook.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.buybook.domain.BuyBookBinding;
import org.agilekip.tutorials.buybook.repository.BuyBookBindingRepository;
import org.agilekip.tutorials.buybook.repository.BuyBookRepository;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.agilekip.tutorials.buybook.service.mapper.BuyBookBindingMapper;
import org.akip.domain.ProcessInstance;
import org.akip.service.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuyBookBinding}.
 */
@Service
@Transactional
public class BuyBookBindingService {

    public static final String BPMN_PROCESS_DEFINITION_ID = "procidBuyBook";

    private final Logger log = LoggerFactory.getLogger(BuyBookBindingService.class);

    private final ProcessInstanceService processInstanceService;

    private final BuyBookRepository buyBookRepository;

    private final BuyBookBindingRepository buyBookBindingRepository;

    private final BuyBookBindingMapper buyBookBindingMapper;

    public BuyBookBindingService(
        ProcessInstanceService processInstanceService,
        BuyBookRepository buyBookRepository,
        BuyBookBindingRepository buyBookBindingRepository,
        BuyBookBindingMapper buyBookBindingMapper
    ) {
        this.processInstanceService = processInstanceService;
        this.buyBookRepository = buyBookRepository;
        this.buyBookBindingRepository = buyBookBindingRepository;
        this.buyBookBindingMapper = buyBookBindingMapper;
    }

    /**
     * Save a buyBookBinding.
     *
     * @param buyBookBindingDTO the entity to save.
     * @return the persisted entity.
     */
    public BuyBookBindingDTO create(BuyBookBindingDTO buyBookBindingDTO) {
        log.debug("Request to save BuyBookBinding : {}", buyBookBindingDTO);

        BuyBookBinding buyBookBinding = buyBookBindingMapper.toEntity(buyBookBindingDTO);

        //Saving the domainEntity
        buyBookRepository.save(buyBookBinding.getBuyBook());

        //Creating the process instance in the Camunda and setting it in the process entity
        ProcessInstance processInstance = processInstanceService.create(
            BPMN_PROCESS_DEFINITION_ID,
            "BuyBook#" + buyBookBinding.getBuyBook().getId(),
            buyBookBinding
        );
        buyBookBinding.setProcessInstance(processInstance);

        //Saving the process entity
        buyBookBinding = buyBookBindingRepository.save(buyBookBinding);
        return buyBookBindingMapper.toDto(buyBookBinding);
    }

    /**
     * Get all the buyBookBindings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BuyBookBindingDTO> findAll() {
        log.debug("Request to get all BuyBookBindings");
        return buyBookBindingRepository
            .findAll()
            .stream()
            .map(buyBookBindingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one buyBookBinding by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BuyBookBindingDTO> findOne(Long id) {
        log.debug("Request to get BuyBookBinding : {}", id);
        return buyBookBindingRepository.findById(id).map(buyBookBindingMapper::toDto);
    }

    /**
     * Get one buyBookBinding by id.
     *
     * @param processInstanceId the id of the processInstance associated to the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BuyBookBindingDTO> findByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get BuyBookBinding by  processInstanceId: {}", processInstanceId);
        return buyBookBindingRepository.findByProcessInstanceId(processInstanceId).map(buyBookBindingMapper::toDto);
    }
}
