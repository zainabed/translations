package org.zainabed.projects.translation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.zainabed.projects.translation.model.Role;
import org.zainabed.projects.translation.model.User;
import org.zainabed.projects.translation.model.UserProjectRole;
import org.zainabed.projects.translation.repository.RoleRepository;
import org.zainabed.projects.translation.repository.UserJpaRepository;
import org.zainabed.projects.translation.repository.UserProjectRoleRepository;

@Component
@RepositoryEventHandler(User.class)
public class UserService {

    @Autowired
    UserProjectRoleRepository userProjectRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @HandleAfterCreate
    public void AfterCreate(User user){
        String userRoleName = "ROLE_USER";
        Role userRole  = roleRepository.findByName(userRoleName);
        UserProjectRole userProjectRole = new UserProjectRole();
        userProjectRole.setUsers(user);
        userProjectRole.setRoles(userRole);
        userProjectRoleRepository.save(userProjectRole);
    }
}
