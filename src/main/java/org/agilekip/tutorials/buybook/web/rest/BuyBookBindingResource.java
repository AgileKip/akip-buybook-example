package org.agilekip.tutorials.buybook.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.agilekip.tutorials.buybook.service.BuyBookBindingService;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.agilekip.tutorials.buybook.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.buybook.domain.BuyBookBinding}.
 */
@RestController
@RequestMapping("/api")
public class BuyBookBindingResource {

    private final Logger log = LoggerFactory.getLogger(BuyBookBindingResource.class);

    private static final String ENTITY_NAME = "buyBookBinding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyBookBindingService buyBookBindingService;

    public BuyBookBindingResource(BuyBookBindingService buyBookBindingService) {
        this.buyBookBindingService = buyBookBindingService;
    }

    /**
     * {@code POST  /buy-book-bindings} : Create a new buyBookBinding.
     *
     * @param buyBookBindingDTO the buyBookBindingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyBookBindingDTO, or with status {@code 400 (Bad Request)} if the buyBookBinding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buy-book-bindings")
    public ResponseEntity<BuyBookBindingDTO> create(@RequestBody BuyBookBindingDTO buyBookBindingDTO) throws URISyntaxException {
        log.debug("REST request to save BuyBookBinding : {}", buyBookBindingDTO);
        if (buyBookBindingDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyBookBinding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyBookBindingDTO result = buyBookBindingService.create(buyBookBindingDTO);
        return ResponseEntity
            .created(new URI("/api/buy-book-bindings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /buy-book-bindings} : get all the buyBookBindings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyBookBindings in body.
     */
    @GetMapping("/buy-book-bindings")
    public List<BuyBookBindingDTO> getAllBuyBookBindings() {
        log.debug("REST request to get all BuyBookBindings");
        return buyBookBindingService.findAll();
    }

    /**
     * {@code GET  /buy-book-bindings/:id} : get the "id" buyBookBinding.
     *
     * @param processInstanceId the id of the buyBookBindingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyBookBindingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buy-book-bindings/{processInstanceId}")
    public ResponseEntity<BuyBookBindingDTO> getByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get BuyBookBinding by processInstanceId : {}", processInstanceId);
        Optional<BuyBookBindingDTO> buyBookBindingDTO = buyBookBindingService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(buyBookBindingDTO);
    }
}
