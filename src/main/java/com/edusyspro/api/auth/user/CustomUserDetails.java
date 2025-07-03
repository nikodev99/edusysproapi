package com.edusyspro.api.auth.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    @Getter
    private final Long id;

    @Getter
    private final Long personalInfoId;

    private final String username;

    @Getter
    private final String email;

    @Getter
    private final String phoneNumber;

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final boolean accountNonLocked;


    protected CustomUserDetails (
            long id, long personalInfoId, String username, String email, String phoneNumber, String password, String firstName, String lastName,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled, boolean accountNonLocked
    ) {
        this.id = id;
        this.personalInfoId = personalInfoId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
    }

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toUnmodifiableList());

        boolean isEnabled = Boolean.TRUE.equals(user.getEnabled());
        boolean isNotLocked = Boolean.TRUE.equals(user.getAccountNonLocked());

        return new CustomUserDetails(
                user.getId(),
                user.getPersonalInfo().getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getPersonalInfo().getFirstName(),
                user.getPersonalInfo().getLastName(),
                authorities,
                isEnabled,
                isNotLocked
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
