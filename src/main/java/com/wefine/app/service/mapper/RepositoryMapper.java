package com.wefine.app.service.mapper;

import com.wefine.app.domain.*;
import com.wefine.app.service.dto.RepositoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Repository and its DTO RepositoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RepositoryMapper {

    RepositoryDTO repositoryToRepositoryDTO(Repository repository);

    List<RepositoryDTO> repositoriesToRepositoryDTOs(List<Repository> repositories);

    Repository repositoryDTOToRepository(RepositoryDTO repositoryDTO);

    List<Repository> repositoryDTOsToRepositories(List<RepositoryDTO> repositoryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Repository repositoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Repository repository = new Repository();
        repository.setId(id);
        return repository;
    }
    

}
