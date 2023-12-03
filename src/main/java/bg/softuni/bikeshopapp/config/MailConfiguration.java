package bg.softuni.bikeshopapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    private String host;
    private int port;
    private String username;
    private String password;

    @Bean
    @ConfigurationProperties(prefix = "mail")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();

        jms.setHost(host);
        jms.setPort(port);
        jms.setUsername(username);
        jms.setPassword(password);
        jms.setDefaultEncoding("UTF-8");

        Properties props = jms.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return jms;
    }
}