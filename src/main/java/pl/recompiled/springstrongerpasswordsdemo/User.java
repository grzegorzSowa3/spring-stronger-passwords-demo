package pl.recompiled.springstrongerpasswordsdemo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class User implements UserDetails {

    @Id
    private UUID id;
    @Transient
    private boolean isNew;
    @Column(unique = true)
    private String username;
    private String password;

    public static User newInstance(String username,
                                   String password) {
        final User user = new User();
        user.id = UUID.randomUUID();
        user.isNew = true;
        user.username = username;
        user.password = password;
        return user;
    }

    public User withPassword(String password) {
        final User user = new User();
        user.id = this.id;
        user.username = this.username;
        user.password = password;
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
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

    UserData toData() {
        final UserData data = new UserData();
        data.setId(id.toString());
        data.setUsername(username);
        return data;
    }
}
