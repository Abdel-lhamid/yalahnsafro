package com.yallahnsafro.yallahnsafrobackend.entities;


import com.yallahnsafro.yallahnsafrobackend.shared.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users", schema = "yallahnsafro_db")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 6407688734661559517L;
    @Id
    @GeneratedValue
    private long id;

    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(nullable = true, unique = true)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true, length = 10)
    private String phonenumber;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private boolean locked = false;

    @Column(nullable = false)
    private boolean enabled = true;

    private String verification_token;

    private boolean email_verification_status = false;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    /**
     * @OneToMany (cascade = CascadeType.ALL)
     * @JoinColumn (name = "user_id", referencedColumnName = "user_id")
     * private List<BookingEntity> bookings;
     *
     */

}
