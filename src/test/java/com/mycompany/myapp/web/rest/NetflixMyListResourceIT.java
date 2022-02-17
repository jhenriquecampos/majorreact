package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NetflixMyList;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.NetflixMyListRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NetflixMyListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NetflixMyListResourceIT {

    private static final Integer DEFAULT_MOVIE_COD = 1;
    private static final Integer UPDATED_MOVIE_COD = 2;

    private static final String ENTITY_API_URL = "/api/netflix-my-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NetflixMyListRepository netflixMyListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNetflixMyListMockMvc;

    private NetflixMyList netflixMyList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetflixMyList createEntity(EntityManager em) {
        NetflixMyList netflixMyList = new NetflixMyList().movieCod(DEFAULT_MOVIE_COD);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        netflixMyList.setUser(user);
        return netflixMyList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetflixMyList createUpdatedEntity(EntityManager em) {
        NetflixMyList netflixMyList = new NetflixMyList().movieCod(UPDATED_MOVIE_COD);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        netflixMyList.setUser(user);
        return netflixMyList;
    }

    @BeforeEach
    public void initTest() {
        netflixMyList = createEntity(em);
    }

    @Test
    @Transactional
    void createNetflixMyList() throws Exception {
        int databaseSizeBeforeCreate = netflixMyListRepository.findAll().size();
        // Create the NetflixMyList
        restNetflixMyListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(netflixMyList)))
            .andExpect(status().isCreated());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeCreate + 1);
        NetflixMyList testNetflixMyList = netflixMyListList.get(netflixMyListList.size() - 1);
        assertThat(testNetflixMyList.getMovieCod()).isEqualTo(DEFAULT_MOVIE_COD);
    }

    @Test
    @Transactional
    void createNetflixMyListWithExistingId() throws Exception {
        // Create the NetflixMyList with an existing ID
        netflixMyList.setId(1L);

        int databaseSizeBeforeCreate = netflixMyListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetflixMyListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(netflixMyList)))
            .andExpect(status().isBadRequest());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMovieCodIsRequired() throws Exception {
        int databaseSizeBeforeTest = netflixMyListRepository.findAll().size();
        // set the field null
        netflixMyList.setMovieCod(null);

        // Create the NetflixMyList, which fails.

        restNetflixMyListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(netflixMyList)))
            .andExpect(status().isBadRequest());

        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNetflixMyLists() throws Exception {
        // Initialize the database
        netflixMyListRepository.saveAndFlush(netflixMyList);

        // Get all the netflixMyListList
        restNetflixMyListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(netflixMyList.getId().intValue())))
            .andExpect(jsonPath("$.[*].movieCod").value(hasItem(DEFAULT_MOVIE_COD)));
    }

    @Test
    @Transactional
    void getNetflixMyList() throws Exception {
        // Initialize the database
        netflixMyListRepository.saveAndFlush(netflixMyList);

        // Get the netflixMyList
        restNetflixMyListMockMvc
            .perform(get(ENTITY_API_URL_ID, netflixMyList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(netflixMyList.getId().intValue()))
            .andExpect(jsonPath("$.movieCod").value(DEFAULT_MOVIE_COD));
    }

    @Test
    @Transactional
    void getNonExistingNetflixMyList() throws Exception {
        // Get the netflixMyList
        restNetflixMyListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNetflixMyList() throws Exception {
        // Initialize the database
        netflixMyListRepository.saveAndFlush(netflixMyList);

        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();

        // Update the netflixMyList
        NetflixMyList updatedNetflixMyList = netflixMyListRepository.findById(netflixMyList.getId()).get();
        // Disconnect from session so that the updates on updatedNetflixMyList are not directly saved in db
        em.detach(updatedNetflixMyList);
        updatedNetflixMyList.movieCod(UPDATED_MOVIE_COD);

        restNetflixMyListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNetflixMyList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNetflixMyList))
            )
            .andExpect(status().isOk());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
        NetflixMyList testNetflixMyList = netflixMyListList.get(netflixMyListList.size() - 1);
        assertThat(testNetflixMyList.getMovieCod()).isEqualTo(UPDATED_MOVIE_COD);
    }

    @Test
    @Transactional
    void putNonExistingNetflixMyList() throws Exception {
        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();
        netflixMyList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetflixMyListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, netflixMyList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netflixMyList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNetflixMyList() throws Exception {
        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();
        netflixMyList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetflixMyListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(netflixMyList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNetflixMyList() throws Exception {
        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();
        netflixMyList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetflixMyListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(netflixMyList)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNetflixMyListWithPatch() throws Exception {
        // Initialize the database
        netflixMyListRepository.saveAndFlush(netflixMyList);

        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();

        // Update the netflixMyList using partial update
        NetflixMyList partialUpdatedNetflixMyList = new NetflixMyList();
        partialUpdatedNetflixMyList.setId(netflixMyList.getId());

        partialUpdatedNetflixMyList.movieCod(UPDATED_MOVIE_COD);

        restNetflixMyListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetflixMyList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetflixMyList))
            )
            .andExpect(status().isOk());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
        NetflixMyList testNetflixMyList = netflixMyListList.get(netflixMyListList.size() - 1);
        assertThat(testNetflixMyList.getMovieCod()).isEqualTo(UPDATED_MOVIE_COD);
    }

    @Test
    @Transactional
    void fullUpdateNetflixMyListWithPatch() throws Exception {
        // Initialize the database
        netflixMyListRepository.saveAndFlush(netflixMyList);

        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();

        // Update the netflixMyList using partial update
        NetflixMyList partialUpdatedNetflixMyList = new NetflixMyList();
        partialUpdatedNetflixMyList.setId(netflixMyList.getId());

        partialUpdatedNetflixMyList.movieCod(UPDATED_MOVIE_COD);

        restNetflixMyListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetflixMyList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNetflixMyList))
            )
            .andExpect(status().isOk());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
        NetflixMyList testNetflixMyList = netflixMyListList.get(netflixMyListList.size() - 1);
        assertThat(testNetflixMyList.getMovieCod()).isEqualTo(UPDATED_MOVIE_COD);
    }

    @Test
    @Transactional
    void patchNonExistingNetflixMyList() throws Exception {
        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();
        netflixMyList.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetflixMyListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, netflixMyList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(netflixMyList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNetflixMyList() throws Exception {
        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();
        netflixMyList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetflixMyListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(netflixMyList))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNetflixMyList() throws Exception {
        int databaseSizeBeforeUpdate = netflixMyListRepository.findAll().size();
        netflixMyList.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetflixMyListMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(netflixMyList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetflixMyList in the database
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNetflixMyList() throws Exception {
        // Initialize the database
        netflixMyListRepository.saveAndFlush(netflixMyList);

        int databaseSizeBeforeDelete = netflixMyListRepository.findAll().size();

        // Delete the netflixMyList
        restNetflixMyListMockMvc
            .perform(delete(ENTITY_API_URL_ID, netflixMyList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NetflixMyList> netflixMyListList = netflixMyListRepository.findAll();
        assertThat(netflixMyListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
