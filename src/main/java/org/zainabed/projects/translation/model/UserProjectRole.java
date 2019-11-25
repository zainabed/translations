package org.zainabed.projects.translation.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user_project_role")
public class UserProjectRole extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id")
    private Role roles;

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Project projects;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User users;

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public Project getProjects() {
        return projects;
    }

    public void setProjects(Project projects) {
        this.projects = projects;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public String getProjectRole(){
        String projectId = projects != null ? projects.getId() + "_" : "";
        return projectId + roles.getName();
    }
}
