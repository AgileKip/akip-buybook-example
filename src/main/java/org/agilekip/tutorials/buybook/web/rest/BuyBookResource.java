package org.agilekip.tutorials.buybook.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.agilekip.tutorials.buybook.repository.BuyBookRepository;
import org.agilekip.tutorials.buybook.service.BuyBookService;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.agilekip.tutorials.buybook.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.buybook.domain.BuyBook}.
 */
@RestController
@RequestMapping("/api")
public class BuyBookResource {

    private final Logger log = LoggerFactory.getLogger(BuyBookResource.class);

    private static final String ENTITY_NAME = "buyBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyBookService buyBookService;

    private final BuyBookRepository buyBookRepository;

    public BuyBookResource(BuyBookService buyBookService, BuyBookRepository buyBookRepository) {
        this.buyBookService = buyBookService;
        this.buyBookRepository = buyBookRepository;
    }

    /**
     * {@code POST  /buy-books} : Create a new buyBook.
     *
     * @param buyBookDTO the buyBookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyBookDTO, or with status {@code 400 (Bad Request)} if the buyBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buy-books")
    public ResponseEntity<BuyBookDTO> createBuyBook(@Valid @RequestBody BuyBookDTO buyBookDTO) throws URISyntaxException {
        log.debug("REST request to save BuyBook : {}", buyBookDTO);
        if (buyBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyBookDTO result = buyBookService.save(buyBookDTO);
        return ResponseEntity
            .created(new URI("/api/buy-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buy-books/:id} : Updates an existing buyBook.
     *
     * @param id the id of the buyBookDTO to save.
     * @param buyBookDTO the buyBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyBookDTO,
     * or with status {@code 400 (Bad Request)} if the buyBookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buy-books/{id}")
    public ResponseEntity<BuyBookDTO> updateBuyBook(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BuyBookDTO buyBookDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BuyBook : {}, {}", id, buyBookDTO);
        if (buyBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuyBookDTO result = buyBookService.save(buyBookDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buy-books/:id} : Partial updates given fields of an existing buyBook, field will ignore if it is null
     *
     * @param id the id of the buyBookDTO to save.
     * @param buyBookDTO the buyBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyBookDTO,
     * or with status {@code 400 (Bad Request)} if the buyBookDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buyBookDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buy-books/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BuyBookDTO> partialUpdateBuyBook(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BuyBookDTO buyBookDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BuyBook partially : {}, {}", id, buyBookDTO);
        if (buyBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyBookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyBookDTO> result = buyBookService.partialUpdate(buyBookDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyBookDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /buy-books} : get all the buyBooks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyBooks in body.
     */
    @GetMapping("/buy-books")
    public List<BuyBookDTO> getAllBuyBooks() {
        log.debug("REST request to get all BuyBooks");
        return buyBookService.findAll();
    }

    /**
     * {@code GET  /buy-books/:id} : get the "id" buyBook.
     *
     * @param id the id of the buyBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyBookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buy-books/{id}")
    public ResponseEntity<BuyBookDTO> getBuyBook(@PathVariable Long id) {
        log.debug("REST request to get BuyBook : {}", id);
        Optional<BuyBookDTO> buyBookDTO = buyBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyBookDTO);
    }

    /**
     * {@code DELETE  /buy-books/:id} : delete the "id" buyBook.
     *
     * @param id the id of the buyBookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buy-books/{id}")
    public ResponseEntity<Void> deleteBuyBook(@PathVariable Long id) {
        log.debug("REST request to delete BuyBook : {}", id);
        buyBookService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
