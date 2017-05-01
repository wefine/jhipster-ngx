package com.wefine.app.web.rest;

import com.wefine.app.TmsApp;

import com.wefine.app.domain.Repository;
import com.wefine.app.repository.RepositoryRepository;
import com.wefine.app.service.RepositoryService;
import com.wefine.app.service.dto.RepositoryDTO;
import com.wefine.app.service.mapper.RepositoryMapper;
import com.wefine.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.wefine.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RepositoryResource REST controller.
 *
 * @see RepositoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmsApp.class)
public class RepositoryResourceIntTest {

    private static final String DEFAULT_GROUP_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ARTIFACT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ARTIFACT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_RELEASE_AT = "AAAAAAAAAA";
    private static final String UPDATED_RELEASE_AT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CHECK_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CHECK_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRepositoryMockMvc;

    private Repository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RepositoryResource repositoryResource = new RepositoryResource(repositoryService);
        this.restRepositoryMockMvc = MockMvcBuilders.standaloneSetup(repositoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repository createEntity(EntityManager em) {
        Repository repository = new Repository()
            .groupId(DEFAULT_GROUP_ID)
            .artifactId(DEFAULT_ARTIFACT_ID)
            .version(DEFAULT_VERSION)
            .releaseAt(DEFAULT_RELEASE_AT)
            .checkAt(DEFAULT_CHECK_AT);
        return repository;
    }

    @Before
    public void initTest() {
        repository = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepository() throws Exception {
        int databaseSizeBeforeCreate = repositoryRepository.findAll().size();

        // Create the Repository
        RepositoryDTO repositoryDTO = repositoryMapper.repositoryToRepositoryDTO(repository);
        restRepositoryMockMvc.perform(post("/api/repositories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repositoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Repository in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeCreate + 1);
        Repository testRepository = repositoryList.get(repositoryList.size() - 1);
        assertThat(testRepository.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testRepository.getArtifactId()).isEqualTo(DEFAULT_ARTIFACT_ID);
        assertThat(testRepository.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testRepository.getReleaseAt()).isEqualTo(DEFAULT_RELEASE_AT);
        assertThat(testRepository.getCheckAt()).isEqualTo(DEFAULT_CHECK_AT);
    }

    @Test
    @Transactional
    public void createRepositoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repositoryRepository.findAll().size();

        // Create the Repository with an existing ID
        repository.setId(1L);
        RepositoryDTO repositoryDTO = repositoryMapper.repositoryToRepositoryDTO(repository);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepositoryMockMvc.perform(post("/api/repositories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repositoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRepositories() throws Exception {
        // Initialize the database
        repositoryRepository.saveAndFlush(repository);

        // Get all the repositoryList
        restRepositoryMockMvc.perform(get("/api/repositories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repository.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.toString())))
            .andExpect(jsonPath("$.[*].artifactId").value(hasItem(DEFAULT_ARTIFACT_ID.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
            .andExpect(jsonPath("$.[*].releaseAt").value(hasItem(DEFAULT_RELEASE_AT.toString())))
            .andExpect(jsonPath("$.[*].checkAt").value(hasItem(sameInstant(DEFAULT_CHECK_AT))));
    }

    @Test
    @Transactional
    public void getRepository() throws Exception {
        // Initialize the database
        repositoryRepository.saveAndFlush(repository);

        // Get the repository
        restRepositoryMockMvc.perform(get("/api/repositories/{id}", repository.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(repository.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.toString()))
            .andExpect(jsonPath("$.artifactId").value(DEFAULT_ARTIFACT_ID.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.releaseAt").value(DEFAULT_RELEASE_AT.toString()))
            .andExpect(jsonPath("$.checkAt").value(sameInstant(DEFAULT_CHECK_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingRepository() throws Exception {
        // Get the repository
        restRepositoryMockMvc.perform(get("/api/repositories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepository() throws Exception {
        // Initialize the database
        repositoryRepository.saveAndFlush(repository);
        int databaseSizeBeforeUpdate = repositoryRepository.findAll().size();

        // Update the repository
        Repository updatedRepository = repositoryRepository.findOne(repository.getId());
        updatedRepository
            .groupId(UPDATED_GROUP_ID)
            .artifactId(UPDATED_ARTIFACT_ID)
            .version(UPDATED_VERSION)
            .releaseAt(UPDATED_RELEASE_AT)
            .checkAt(UPDATED_CHECK_AT);
        RepositoryDTO repositoryDTO = repositoryMapper.repositoryToRepositoryDTO(updatedRepository);

        restRepositoryMockMvc.perform(put("/api/repositories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repositoryDTO)))
            .andExpect(status().isOk());

        // Validate the Repository in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeUpdate);
        Repository testRepository = repositoryList.get(repositoryList.size() - 1);
        assertThat(testRepository.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testRepository.getArtifactId()).isEqualTo(UPDATED_ARTIFACT_ID);
        assertThat(testRepository.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testRepository.getReleaseAt()).isEqualTo(UPDATED_RELEASE_AT);
        assertThat(testRepository.getCheckAt()).isEqualTo(UPDATED_CHECK_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingRepository() throws Exception {
        int databaseSizeBeforeUpdate = repositoryRepository.findAll().size();

        // Create the Repository
        RepositoryDTO repositoryDTO = repositoryMapper.repositoryToRepositoryDTO(repository);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRepositoryMockMvc.perform(put("/api/repositories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repositoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Repository in the database
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRepository() throws Exception {
        // Initialize the database
        repositoryRepository.saveAndFlush(repository);
        int databaseSizeBeforeDelete = repositoryRepository.findAll().size();

        // Get the repository
        restRepositoryMockMvc.perform(delete("/api/repositories/{id}", repository.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Repository> repositoryList = repositoryRepository.findAll();
        assertThat(repositoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Repository.class);
    }
}
