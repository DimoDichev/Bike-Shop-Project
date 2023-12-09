package bg.softuni.bikeshopapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class FileValidator implements ConstraintValidator<FileAnnotation, MultipartFile> {

    private List<String> contentType;
    private Long size;

    @Override
    public void initialize(FileAnnotation constraintAnnotation) {
        this.size = constraintAnnotation.size();
        this.contentType = Arrays.stream(constraintAnnotation.contentTypes()).toList();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String errorMsg = getMsg(file);

        if (!errorMsg.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMsg)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

    private String getMsg(MultipartFile file) {
        if (file.isEmpty()) {
            return "File must be provided";
        }

        if (file.getSize() > size) {
            return "Exceeded file size. Max size: " + size;
        }

        if (!contentType.contains(file.getContentType())) {
            return "Invalid file extension. Supported files: " + String.join(", ", contentType);
        }

        return "";
    }
}
