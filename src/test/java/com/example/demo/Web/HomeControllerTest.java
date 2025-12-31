package com.example.demo.Web;

import com.example.demo.Data.User.User;
import com.example.demo.Data.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    void postSignIn_validCredentials_setsSessionAndReturnsMain() throws Exception {
        User fake = new User();
        fake.setId(1l);
        fake.setEmail("test@gmail.com");

        when(userService.login("test@gmail.com", "12345678")).thenReturn(fake);

        MockHttpSession session = new MockHttpSession();

        mvc.perform(MockMvcRequestBuilders.post("/sign_in")
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test@gmail.com")
                .param("password", "12345678"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(request().sessionAttribute("currentUser", fake));
        verify(userService).login("test@gmail.com","12345678");
    }

    @Test
    void postSignIn_invalidEmail_showsSignInWithError_andDoesNotCallService() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/sign_in")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test@gmail.com")
                .param("password", "12345678"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign_in"))
                .andExpect(model().attributeExists("error"));
        verifyNoInteractions(userService);
    }

    @Test
    void postSignIn_shortPassword_showsSignInWithError_andDoesNotCallService() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/sign_in")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test@gmail.com")
                .param("password", "12345678"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign_in"))
                .andExpect(model().attributeExists("error"));
        verifyNoInteractions(userService);
    }
    @Test
    void postSignIn_serviceThrows_showsSignInWithServiceError() throws Exception {
        when(userService.login("test@gmail.com", "12345678"))
                .thenThrow(new IllegalArgumentException("Wrong credentials"));

        mvc.perform(MockMvcRequestBuilders.post("/sign_in")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@gmail.com")
                        .param("password", "12345678"))
                .andExpect(status().isOk())
                .andExpect(view().name("sign_in"))
                .andExpect(model().attribute("error", "Wrong credentials"));
    }
}


