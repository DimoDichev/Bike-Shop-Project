package bg.softuni.bikeshopapp.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String url;
    @ManyToOne()
    private BikeEntity bike;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public BikeEntity getBike() {
        return bike;
    }

    public PictureEntity setBike(BikeEntity bike) {
        this.bike = bike;
        return this;
    }
}
