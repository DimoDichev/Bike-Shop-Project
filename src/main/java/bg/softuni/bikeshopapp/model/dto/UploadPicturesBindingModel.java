package bg.softuni.bikeshopapp.model.dto;

import bg.softuni.bikeshopapp.validation.FileAnnotation;
import org.springframework.web.multipart.MultipartFile;

public class UploadPicturesBindingModel {

    private Long id;

    @FileAnnotation(contentTypes = {"image/png", "image/jpeg"})
    private MultipartFile pictures;

    public UploadPicturesBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public UploadPicturesBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public MultipartFile getPictures() {
        return pictures;
    }

    public UploadPicturesBindingModel setPictures(MultipartFile pictures) {
        this.pictures = pictures;
        return this;
    }
}
