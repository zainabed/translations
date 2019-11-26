package org.zainabed.projects.translation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zainabed.projects.translation.model.UserProjectRole;
import org.zainabed.projects.translation.model.projection.AccessView;
import org.zainabed.projects.translation.repository.UserProjectRoleRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/access")
public class AccessController {

    Logger logger = Logger.getLogger(AccessController.class.getName());
    @Autowired
    UserProjectRoleRepository userProjectRoleRepository;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<AccessView> getAll(){
        try {
            return userProjectRoleRepository.findAll().stream().map(AccessView::getInstance).collect(Collectors.toList());
        }catch(Exception e){
            logger.warning(e.getMessage());
            return null;
        }
    }

    @RequestMapping("/project/{projectId}")
    public List<AccessView> findByProjectId(@PathVariable("projectId") Long projectId){
        return userProjectRoleRepository.findByProjects_Id(projectId).stream().map(AccessView::getInstance).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    public AccessView save(UserProjectRole userProjectRole){
        userProjectRole = userProjectRoleRepository.save(userProjectRole);
        return AccessView.getInstance(userProjectRole);
    }
}
