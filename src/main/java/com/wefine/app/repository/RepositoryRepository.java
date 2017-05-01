package com.wefine.app.repository;

import com.wefine.app.domain.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Repository entity.
 */
@SuppressWarnings("unused")
public interface RepositoryRepository extends JpaRepository<Repository,Long> {

}
