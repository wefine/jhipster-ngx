package com.wefine.app.service;

import com.wefine.app.service.dto.RepositoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Repository.
 */
public interface RepositoryService {

    /**
     * Save a repository.
     *
     * @param repositoryDTO the entity to save
     * @return the persisted entity
     */
    RepositoryDTO save(RepositoryDTO repositoryDTO);

    /**
     *  Get all the repositories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RepositoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" repository.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RepositoryDTO findOne(Long id);

    /**
     *  Delete the "id" repository.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
