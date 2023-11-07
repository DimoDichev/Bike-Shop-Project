package bg.softuni.bikeshopapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_us")
public class ContactUsEntity extends BaseEntity {

    @Column(nullable = false)
    private String email;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    public ContactUsEntity() {
    }

    public String getEmail() {
        return email;
    }

    public ContactUsEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ContactUsEntity setMessage(String message) {
        this.message = message;
        return this;
    }
}
