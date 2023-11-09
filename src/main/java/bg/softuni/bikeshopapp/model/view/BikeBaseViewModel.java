package bg.softuni.bikeshopapp.model.view;

public class BikeBaseViewModel {
    private Long id;
    private String manufacturer;
    private String model;
    private String imageUrl;

    public BikeBaseViewModel() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public BikeBaseViewModel setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public Long getId() {
        return id;
    }

    public BikeBaseViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModel() {
        return model;
    }

    public BikeBaseViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BikeBaseViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
