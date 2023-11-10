package bg.softuni.bikeshopapp.model.view;

public class ManufacturerNameViewModel {
    private Long id;
    private String name;

    public ManufacturerNameViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ManufacturerNameViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ManufacturerNameViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
