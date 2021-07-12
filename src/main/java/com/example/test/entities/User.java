package com.example.test.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails
{
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String verificationCode;
    private boolean enabled;

    @Lob
    @Setter(AccessLevel.NONE)
    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String password, String name, String email, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    public void setImage(byte[] image) throws UnsupportedEncodingException {
        this.image = new String(java.util.Base64.getEncoder().encode(image), "UTF-8");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
