package io.nuwe.FemHack_JavaFemCoders.model.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Document(collection = "users")
public class User implements UserDetails, Serializable {

    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String password;
    private String verificationCode;
    private List<UserConnection> userConnections;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email) {
        this.email = email;
    }

    public User() {
        this.userConnections = new ArrayList<>();
    }

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
        return email;
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

    /**
     * Method to add a userConnection to the list of userConnections.
     * Used in logConnection method in the UserConnectionService layer.
     */
    public void addUserConnections(UserConnection userConnection) {
        this.userConnections.add(userConnection);
    }

}
