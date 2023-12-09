package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImg(MultipartFile multipartFile) throws IOException {
        return cloudinary
                .uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }

    @Override
    public void delete(String url) throws IOException {
        cloudinary
                .uploader()
                .destroy("url", ObjectUtils.emptyMap());
    }
}
