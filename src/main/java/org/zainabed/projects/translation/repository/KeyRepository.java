package org.zainabed.projects.translation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.zainabed.projects.translation.model.Key;
import org.zainabed.projects.translation.model.Locale;
import org.zainabed.projects.translation.model.Project;

@PreAuthorize("hasRole('ROLE_USER')")
public interface KeyRepository extends JpaRepository<Key, Long> {

    @RestResource(path = "name", rel = "keys")
    List<Key> findByNameContainingAndProjectsId(@Param("name") String name, @Param("projects") Long projects);

    List<Key> findAllByProjectsId(@Param("projects") Long projects);

    List<Key> findAllByNameInAndProjectsId(@Param("name") List<String> codes, @Param("projects") Long projects);

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
    @Override
    Key save(Key entity);

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
    void deleteById(Long aLong);

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
    void delete(Key key);

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PO')")
    void deleteAll();
}
