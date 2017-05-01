package com.wefine.app.service.impl;

import com.wefine.app.service.RepositoryService;
import com.wefine.app.domain.Repository;
import com.wefine.app.repository.RepositoryRepository;
import com.wefine.app.service.dto.RepositoryDTO;
import com.wefine.app.service.mapper.RepositoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Repository.
 */
@Service
@Transactional
public class RepositoryServiceImpl implements RepositoryService{

    private final Logger log = LoggerFactory.getLogger(RepositoryServiceImpl.class);
    
    private final RepositoryRepository repositoryRepository;

    private final RepositoryMapper repositoryMapper;

    public RepositoryServiceImpl(RepositoryRepository repositoryRepository, RepositoryMapper repositoryMapper) {
        this.repositoryRepository = repositoryRepository;
        this.repositoryMapper = repositoryMapper;
    }

    /**
     * Save a repository.
     *
     * @param repositoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RepositoryDTO save(RepositoryDTO repositoryDTO) {
        log.debug("Request to save Repository : {}", repositoryDTO);
        Repository repository = repositoryMapper.repositoryDTOToRepository(repositoryDTO);
        repository = repositoryRepository.save(repository);
        RepositoryDTO result = repositoryMapper.repositoryToRepositoryDTO(repository);
        return result;
    }

    /**
     *  Get all the repositories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RepositoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Repositories");
        Page<Repository> result = repositoryRepository.findAll(pageable);
        return result.map(repository -> repositoryMapper.repositoryToRepositoryDTO(repository));
    }

    /**
     *  Get one repository by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RepositoryDTO findOne(Long id) {
        log.debug("Request to get Repository : {}", id);
        Repository repository = repositoryRepository.findOne(id);
        RepositoryDTO repositoryDTO = repositoryMapper.repositoryToRepositoryDTO(repository);
        return repositoryDTO;
    }

    /**
     *  Delete the  repository by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Repository : {}", id);
        repositoryRepository.delete(id);
    }
}
