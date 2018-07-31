package com.mytest.demo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class DemoUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long userId;

    @Column(nullable = false, unique = true, length = 16)
    private String username;

    @Column(nullable = false, length = 128)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_map", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {
            @JoinColumn(name = "roleId")})
    private Set<DemoRole> roles;

    public DemoUser() {
    }

    public DemoUser(String username, String password, Set<DemoRole> roles) {
        super();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<DemoRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<DemoRole> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (!CollectionUtils.isEmpty(roles)) {
            List<GrantedAuthority> auths = new ArrayList<>();
            roles.forEach(r -> {
                GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + r.getRoleName());
                auths.add(auth);
            });

            return auths;
        }

        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "DemoUser [userId=" + userId + ", username=" + username + ", password=xxx, roles=" + roles + "]";
    }
}