package bg.softuni.bikeshopapp.model.view;

import java.util.List;

public class BikeDetailsViewModel {

    private String manufacturer;
    private String model;
    private String size;
    private String frameMaterial;
    private String wheelSize;
    private String description;
    private List<String> imagesUrl;
    private Double price;

    public BikeDetailsViewModel() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public BikeDetailsViewModel setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getModel() {
        return model;
    }

    public BikeDetailsViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public String getSize() {
        return size;
    }

    public BikeDetailsViewModel setSize(String size) {
        this.size = size;
        return this;
    }

    public String getFrameMaterial() {
        return frameMaterial;
    }

    public BikeDetailsViewModel setFrameMaterial(String frameMaterial) {
        this.frameMaterial = frameMaterial;
        return this;
    }

    public String getWheelSize() {
        return wheelSize;
    }

    public BikeDetailsViewModel setWheelSize(String wheelSize) {
        this.wheelSize = wheelSize;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BikeDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public BikeDetailsViewModel setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public BikeDetailsViewModel setPrice(Double price) {
        this.price = price;
        return this;
    }
}
