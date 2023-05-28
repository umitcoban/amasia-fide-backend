package com.umityasincoban.amasia_fide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "citizen_number"),
        @UniqueConstraint(columnNames = "phone"),
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(name = "users_user_id_seq", sequenceName = "users_user_id_seq", initialValue = 1,
            allocationSize = 1)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "first_name", length = 250, nullable = false)
    @Size(min = 1, max = 250)
    @NotBlank()
    private String firstName;

    @Column(name = "middle_name", length = 250)
    private String middleName;

    @Column(name = "last_name", nullable = false ,length = 250)
    @NotBlank()
    @Size(min = 1, max = 250)
    private String lastName;

    @Column(name = "citizen_number", nullable = false ,length = 11, unique = true)
    @NotBlank()
    @Size(min = 11, max = 11)
    private String citizenNumber;

    @Column(name = "email", nullable = false, length = 250, unique = true)
    @Email()
    @Size(min = 5, max=250)
    private String email;

    @Column(name = "phone", nullable = false, length = 17, unique = true)
    @NotBlank()
    private String phone;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(nullable = false, length = 200)
    @NotBlank()
    @Size(min = 6, max = 200)
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name= "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @FutureOrPresent
    private ZonedDateTime createdAt;

    @Column(name= "updated_at", nullable = false)
    @UpdateTimestamp
    @FutureOrPresent
    private ZonedDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<GrantedAuthority>();
        this.roles.forEach(role -> new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
