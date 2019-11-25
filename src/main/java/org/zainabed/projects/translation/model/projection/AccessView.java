package org.zainabed.projects.translation.model.projection;

import org.springframework.beans.factory.annotation.Value;
import org.zainabed.projects.translation.model.User;
import org.zainabed.projects.translation.model.UserProjectRole;

public class AccessView {
    private Long id;
    private Long userId;
    private Long projectId;
    private Long roleId;

    private AccessView(UserProjectRole userProjectRole){
        id = userProjectRole.getId();
        userId = userProjectRole.getUsers().getId();
        projectId = userProjectRole.getProjects().getId();
        roleId = userProjectRole.getRoles().getId();
    }

    public static AccessView getInstance(UserProjectRole userProjectRole){
        return new AccessView(userProjectRole);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
