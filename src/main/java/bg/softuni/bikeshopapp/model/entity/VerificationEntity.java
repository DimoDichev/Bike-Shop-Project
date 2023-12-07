package bg.softuni.bikeshopapp.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "varification_tokens")
public class VerificationEntity extends BaseEntity {

    @Column
    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    public VerificationEntity() {
    }

    public VerificationEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public VerificationEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public VerificationEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

}


