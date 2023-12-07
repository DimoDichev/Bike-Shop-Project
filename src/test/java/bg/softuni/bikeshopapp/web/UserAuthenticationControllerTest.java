//package bg.softuni.bikeshopapp.web;
//
//import com.icegreen.greenmail.util.GreenMail;
//import com.icegreen.greenmail.util.ServerSetup;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserAuthenticationControllerTest {
//
////    @Autowired
////    private MockMvc mockMvc;
////
////    @Value("${mail.port}")
////    private int port;
////
////    @Value("${mail.host}")
////    private String host;
////
////    @Value("${mail.username}")
////    private String username;
////
////    @Value("${mail.password}")
////    private String password;
////
////    private GreenMail greenMail;
//
//    @BeforeEach
//    void setUp() {
////        greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
////        greenMail.start();
////        greenMail.setUser(username, password);
//    }
//
//    @AfterEach
//    void tearDown() {
////        greenMail.stop();
//    }
//
//    @Test
//    void testGetRegister() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/users/register")
//        ).andExpect(status().is3xxRedirection());
//    }
//
//}