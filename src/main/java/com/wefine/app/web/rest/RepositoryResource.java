package com.wefine.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wefine.app.service.RepositoryService;
import com.wefine.app.web.rest.util.HeaderUtil;
import com.wefine.app.web.rest.util.PaginationUtil;
import com.wefine.app.service.dto.RepositoryDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Repository.
 */
@RestController
@RequestMapping("/api")
public class RepositoryResource {

    private final Logger log = LoggerFactory.getLogger(RepositoryResource.class);

    private static final String ENTITY_NAME = "repository";
        
    private final RepositoryService repositoryService;

    public RepositoryResource(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * POST  /repositories : Create a new repository.
     *
     * @param repositoryDTO the repositoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new repositoryDTO, or with status 400 (Bad Request) if the repository has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/repositories")
    @Timed
    public ResponseEntity<RepositoryDTO> createRepository(@RequestBody RepositoryDTO repositoryDTO) throws URISyntaxException {
        log.debug("REST request to save Repository : {}", repositoryDTO);
        if (repositoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new repository cannot already have an ID")).body(null);
        }
        RepositoryDTO result = repositoryService.save(repositoryDTO);
        return ResponseEntity.created(new URI("/api/repositories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /repositories : Updates an existing repository.
     *
     * @param repositoryDTO the repositoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated repositoryDTO,
     * or with status 400 (Bad Request) if the repositoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the repositoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/repositories")
    @Timed
    public ResponseEntity<RepositoryDTO> updateRepository(@RequestBody RepositoryDTO repositoryDTO) throws URISyntaxException {
        log.debug("REST request to update Repository : {}", repositoryDTO);
        if (repositoryDTO.getId() == null) {
            return createRepository(repositoryDTO);
        }
        RepositoryDTO result = repositoryService.save(repositoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, repositoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /repositories : get all the repositories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of repositories in body
     */
    @GetMapping("/repositories")
    @Timed
    public ResponseEntity<List<RepositoryDTO>> getAllRepositories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Repositories");
        Page<RepositoryDTO> page = repositoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/repositories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /repositories/:id : get the "id" repository.
     *
     * @param id the id of the repositoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the repositoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/repositories/{id}")
    @Timed
    public ResponseEntity<RepositoryDTO> getRepository(@PathVariable Long id) {
        log.debug("REST request to get Repository : {}", id);
        RepositoryDTO repositoryDTO = repositoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(repositoryDTO));
    }

    /**
     * DELETE  /repositories/:id : delete the "id" repository.
     *
     * @param id the id of the repositoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/repositories/{id}")
    @Timed
    public ResponseEntity<Void> deleteRepository(@PathVariable Long id) {
        log.debug("REST request to delete Repository : {}", id);
        repositoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
