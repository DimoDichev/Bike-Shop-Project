package bg.softuni.bikeshopapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadImg(MultipartFile multipartFile) throws IOException;

    void delete(String url) throws IOException;
}
