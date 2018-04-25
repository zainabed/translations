package org.zainabed.projects.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zainabed.projects.translation.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
