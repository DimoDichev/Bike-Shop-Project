package bg.softuni.bikeshopapp.web.rest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/blog")
public class BlogViewer {

    @GetMapping("")
    public ModelAndView getBlog() {
        return new ModelAndView("blog");
    }

    @GetMapping(value = "/reconstruction",
    produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] underReconstruction () throws IOException {
        InputStream stream = getClass()
                .getResourceAsStream("/static/images/under-reconstruction.jpg");
        return IOUtils.toByteArray(stream);
    }

}
