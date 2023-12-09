package bg.softuni.bikeshopapp.config;

import bg.softuni.bikeshopapp.interceptor.UserBlockInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserBlockInterceptor userBlockInterceptor;

    public WebConfig(UserBlockInterceptor userBlockInterceptor) {
        this.userBlockInterceptor = userBlockInterceptor;
    }

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(userBlockInterceptor);
    }

}