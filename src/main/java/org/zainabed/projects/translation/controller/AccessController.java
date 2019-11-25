package org.zainabed.projects.translation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zainabed.projects.translation.model.UserProjectRole;
import org.zainabed.projects.translation.model.projection.AccessView;
import org.zainabed.projects.translation.repository.UserProjectRoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/access")
public class AccessController {
    @Autowired
    UserProjectRoleRepository userProjectRoleRepository;

    @RequestMapping("")
    public List<AccessView> getAll(){
        return userProjectRoleRepository.findAll().stream().map(AccessView::getInstance).collect(Collectors.toList());
    }
}
