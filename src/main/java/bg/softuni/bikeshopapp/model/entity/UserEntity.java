package bg.softuni.bikeshopapp.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRoleEntity> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String email, String password, String firstName, String lastName, Set<UserRoleEntity> roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }
}
