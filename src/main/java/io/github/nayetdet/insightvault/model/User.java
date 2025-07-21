package io.github.nayetdet.insightvault.model;

import io.github.nayetdet.insightvault.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_username", columnList = "username", unique = true),
                @Index(name = "idx_email", columnList = "email", unique = true)
        }
)
public class User extends BaseModel implements UserDetails {

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Dataset> datasets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Insight> insights;

    public List<String> getRoles() {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return role == UserRole.USER
                ? List.of(userAuthority)
                : List.of(userAuthority, adminAuthority);
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

}
