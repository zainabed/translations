package org.zainabed.projects.translation.model.projection;

import org.springframework.beans.factory.annotation.Value;
import org.zainabed.projects.translation.model.User;

public interface UserProjectRoleView {
    /*@Value("#{target.getRoles().getId()}")
    Long getRolesId();
*/
   /* @Value("#{target.getUsers().getUsername()}")
    String getUsersUsername();
*/
  /*  @Value("#{target.getProjects().getId()}")
    Long getProjectsId();
*/
    @Value("#{target.getUsers().getId()}")
    Long getUsersId();

    @Value("#{target.getProjects().getId()}")
    Long getProjectsId();
}
