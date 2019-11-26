package org.zainabed.projects.translation.model.projection;

import org.springframework.beans.factory.annotation.Value;
import org.zainabed.projects.translation.model.User;
import org.zainabed.projects.translation.model.UserProjectRole;

public class AccessView {
    private Long id;
    private Long userId;
    private String username;
    private Long projectId;
    private Long roleId;

    private AccessView(UserProjectRole userProjectRole){
        id = userProjectRole.getId() != null ? userProjectRole.getId() : null;
        userId = userProjectRole.getUsers() != null? userProjectRole.getUsers().getId() : null;
        projectId = userProjectRole.getProjects() != null ? userProjectRole.getProjects().getId() : null;
        roleId = userProjectRole.getRoles() != null ? userProjectRole.getRoles().getId() : null;
        username = userProjectRole.getUsers() != null ? userProjectRole.getUsers().getUsername() : null;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
