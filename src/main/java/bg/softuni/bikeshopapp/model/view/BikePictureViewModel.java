package bg.softuni.bikeshopapp.model.view;

import java.util.ArrayList;
import java.util.List;

public class BikePictureViewModel {

    private List<String> picturesUrl;

    public BikePictureViewModel() {
        picturesUrl = new ArrayList<>();
    }

    public List<String> getPicturesUrl() {
        return picturesUrl;
    }

    public BikePictureViewModel setPicturesUrl(List<String> picturesUrl) {
        this.picturesUrl = picturesUrl;
        return this;
    }
}
