package org.agilekip.tutorials.buybook.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.agilekip.tutorials.buybook.IntegrationTest;
import org.agilekip.tutorials.buybook.domain.BuyBook;
import org.agilekip.tutorials.buybook.repository.BuyBookRepository;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.agilekip.tutorials.buybook.service.mapper.BuyBookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BuyBookResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyBookResourceIT {

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELIVERY_HOME = false;
    private static final Boolean UPDATED_DELIVERY_HOME = true;

    private static final String DEFAULT_DELIVERY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_REVIEW = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/buy-books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuyBookRepository buyBookRepository;

    @Autowired
    private BuyBookMapper buyBookMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyBookMockMvc;

    private BuyBook buyBook;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyBook createEntity(EntityManager em) {
        BuyBook buyBook = new BuyBook()
            .purpose(DEFAULT_PURPOSE)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .deliveryHome(DEFAULT_DELIVERY_HOME)
            .deliveryAddress(DEFAULT_DELIVERY_ADDRESS)
            .paymentDetails(DEFAULT_PAYMENT_DETAILS)
            .purchaseReview(DEFAULT_PURCHASE_REVIEW);
        return buyBook;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyBook createUpdatedEntity(EntityManager em) {
        BuyBook buyBook = new BuyBook()
            .purpose(UPDATED_PURPOSE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .deliveryHome(UPDATED_DELIVERY_HOME)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .paymentDetails(UPDATED_PAYMENT_DETAILS)
            .purchaseReview(UPDATED_PURCHASE_REVIEW);
        return buyBook;
    }

    @BeforeEach
    public void initTest() {
        buyBook = createEntity(em);
    }

    @Test
    @Transactional
    void createBuyBook() throws Exception {
        int databaseSizeBeforeCreate = buyBookRepository.findAll().size();
        // Create the BuyBook
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);
        restBuyBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyBookDTO)))
            .andExpect(status().isCreated());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeCreate + 1);
        BuyBook testBuyBook = buyBookList.get(buyBookList.size() - 1);
        assertThat(testBuyBook.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testBuyBook.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBuyBook.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBuyBook.getDeliveryHome()).isEqualTo(DEFAULT_DELIVERY_HOME);
        assertThat(testBuyBook.getDeliveryAddress()).isEqualTo(DEFAULT_DELIVERY_ADDRESS);
        assertThat(testBuyBook.getPaymentDetails()).isEqualTo(DEFAULT_PAYMENT_DETAILS);
        assertThat(testBuyBook.getPurchaseReview()).isEqualTo(DEFAULT_PURCHASE_REVIEW);
    }

    @Test
    @Transactional
    void createBuyBookWithExistingId() throws Exception {
        // Create the BuyBook with an existing ID
        buyBook.setId(1L);
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        int databaseSizeBeforeCreate = buyBookRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyBookRepository.findAll().size();
        // set the field null
        buyBook.setPurpose(null);

        // Create the BuyBook, which fails.
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        restBuyBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyBookDTO)))
            .andExpect(status().isBadRequest());

        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuyBooks() throws Exception {
        // Initialize the database
        buyBookRepository.saveAndFlush(buyBook);

        // Get all the buyBookList
        restBuyBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryHome").value(hasItem(DEFAULT_DELIVERY_HOME.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryAddress").value(hasItem(DEFAULT_DELIVERY_ADDRESS)))
            .andExpect(jsonPath("$.[*].paymentDetails").value(hasItem(DEFAULT_PAYMENT_DETAILS)))
            .andExpect(jsonPath("$.[*].purchaseReview").value(hasItem(DEFAULT_PURCHASE_REVIEW)));
    }

    @Test
    @Transactional
    void getBuyBook() throws Exception {
        // Initialize the database
        buyBookRepository.saveAndFlush(buyBook);

        // Get the buyBook
        restBuyBookMockMvc
            .perform(get(ENTITY_API_URL_ID, buyBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyBook.getId().intValue()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.deliveryHome").value(DEFAULT_DELIVERY_HOME.booleanValue()))
            .andExpect(jsonPath("$.deliveryAddress").value(DEFAULT_DELIVERY_ADDRESS))
            .andExpect(jsonPath("$.paymentDetails").value(DEFAULT_PAYMENT_DETAILS))
            .andExpect(jsonPath("$.purchaseReview").value(DEFAULT_PURCHASE_REVIEW));
    }

    @Test
    @Transactional
    void getNonExistingBuyBook() throws Exception {
        // Get the buyBook
        restBuyBookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBuyBook() throws Exception {
        // Initialize the database
        buyBookRepository.saveAndFlush(buyBook);

        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();

        // Update the buyBook
        BuyBook updatedBuyBook = buyBookRepository.findById(buyBook.getId()).get();
        // Disconnect from session so that the updates on updatedBuyBook are not directly saved in db
        em.detach(updatedBuyBook);
        updatedBuyBook
            .purpose(UPDATED_PURPOSE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .deliveryHome(UPDATED_DELIVERY_HOME)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .paymentDetails(UPDATED_PAYMENT_DETAILS)
            .purchaseReview(UPDATED_PURCHASE_REVIEW);
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(updatedBuyBook);

        restBuyBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyBookDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyBookDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
        BuyBook testBuyBook = buyBookList.get(buyBookList.size() - 1);
        assertThat(testBuyBook.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testBuyBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyBook.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBuyBook.getDeliveryHome()).isEqualTo(UPDATED_DELIVERY_HOME);
        assertThat(testBuyBook.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testBuyBook.getPaymentDetails()).isEqualTo(UPDATED_PAYMENT_DETAILS);
        assertThat(testBuyBook.getPurchaseReview()).isEqualTo(UPDATED_PURCHASE_REVIEW);
    }

    @Test
    @Transactional
    void putNonExistingBuyBook() throws Exception {
        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();
        buyBook.setId(count.incrementAndGet());

        // Create the BuyBook
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyBookDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyBook() throws Exception {
        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();
        buyBook.setId(count.incrementAndGet());

        // Create the BuyBook
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyBook() throws Exception {
        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();
        buyBook.setId(count.incrementAndGet());

        // Create the BuyBook
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyBookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyBookDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyBookWithPatch() throws Exception {
        // Initialize the database
        buyBookRepository.saveAndFlush(buyBook);

        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();

        // Update the buyBook using partial update
        BuyBook partialUpdatedBuyBook = new BuyBook();
        partialUpdatedBuyBook.setId(buyBook.getId());

        partialUpdatedBuyBook
            .description(UPDATED_DESCRIPTION)
            .deliveryHome(UPDATED_DELIVERY_HOME)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .paymentDetails(UPDATED_PAYMENT_DETAILS)
            .purchaseReview(UPDATED_PURCHASE_REVIEW);

        restBuyBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyBook))
            )
            .andExpect(status().isOk());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
        BuyBook testBuyBook = buyBookList.get(buyBookList.size() - 1);
        assertThat(testBuyBook.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testBuyBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyBook.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBuyBook.getDeliveryHome()).isEqualTo(UPDATED_DELIVERY_HOME);
        assertThat(testBuyBook.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testBuyBook.getPaymentDetails()).isEqualTo(UPDATED_PAYMENT_DETAILS);
        assertThat(testBuyBook.getPurchaseReview()).isEqualTo(UPDATED_PURCHASE_REVIEW);
    }

    @Test
    @Transactional
    void fullUpdateBuyBookWithPatch() throws Exception {
        // Initialize the database
        buyBookRepository.saveAndFlush(buyBook);

        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();

        // Update the buyBook using partial update
        BuyBook partialUpdatedBuyBook = new BuyBook();
        partialUpdatedBuyBook.setId(buyBook.getId());

        partialUpdatedBuyBook
            .purpose(UPDATED_PURPOSE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .deliveryHome(UPDATED_DELIVERY_HOME)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .paymentDetails(UPDATED_PAYMENT_DETAILS)
            .purchaseReview(UPDATED_PURCHASE_REVIEW);

        restBuyBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyBook))
            )
            .andExpect(status().isOk());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
        BuyBook testBuyBook = buyBookList.get(buyBookList.size() - 1);
        assertThat(testBuyBook.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testBuyBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBuyBook.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBuyBook.getDeliveryHome()).isEqualTo(UPDATED_DELIVERY_HOME);
        assertThat(testBuyBook.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testBuyBook.getPaymentDetails()).isEqualTo(UPDATED_PAYMENT_DETAILS);
        assertThat(testBuyBook.getPurchaseReview()).isEqualTo(UPDATED_PURCHASE_REVIEW);
    }

    @Test
    @Transactional
    void patchNonExistingBuyBook() throws Exception {
        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();
        buyBook.setId(count.incrementAndGet());

        // Create the BuyBook
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyBookDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyBook() throws Exception {
        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();
        buyBook.setId(count.incrementAndGet());

        // Create the BuyBook
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyBookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyBook() throws Exception {
        int databaseSizeBeforeUpdate = buyBookRepository.findAll().size();
        buyBook.setId(count.incrementAndGet());

        // Create the BuyBook
        BuyBookDTO buyBookDTO = buyBookMapper.toDto(buyBook);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyBookMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(buyBookDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyBook in the database
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyBook() throws Exception {
        // Initialize the database
        buyBookRepository.saveAndFlush(buyBook);

        int databaseSizeBeforeDelete = buyBookRepository.findAll().size();

        // Delete the buyBook
        restBuyBookMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyBook.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuyBook> buyBookList = buyBookRepository.findAll();
        assertThat(buyBookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
