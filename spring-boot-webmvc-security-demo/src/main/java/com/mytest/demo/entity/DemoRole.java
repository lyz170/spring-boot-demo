package com.mytest.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class DemoRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long roleId;

    @Column(nullable = false, unique = true, length = 16)
    private String roleName;

    @Column(length = 128)
    private String description;

// @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
// private Set<DemoUser> users;

    public DemoRole() {
    }

    public DemoRole(Long roleId) {
        super();
        this.roleId = roleId;
    }

    public DemoRole(String roleName, String description) {
        super();
        this.roleName = roleName;
        this.description = description;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DemoRole [roleId=" + roleId + ", roleName=" + roleName + ", description=" + description + "]";
    }
}