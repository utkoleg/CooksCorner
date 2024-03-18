package com.example.cookscorner.entities;

import com.example.cookscorner.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String email;

    private String password;

    private String imageUrl;

    private String bio;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Recipe> recipes;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Recipe> savedRecipes;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Recipe> likedRecipes;

    @ManyToMany(fetch = FetchType.EAGER)
    List<User> followers;

    @ManyToMany(fetch = FetchType.EAGER)
    List<User> following;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = false;

    private String activationToken;

    private String passwordResetToken;

    private String name;

    private String surname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return this.enabled; // Return the enabled status for the user
    }

    // Generate a verification token for the user
    public void generateActivationToken() {
        this.activationToken = UUID.randomUUID().toString();
    }
}
