package nl.christine.schwartze.server.test.mock;

import nl.christine.schwartze.server.controller.LetterGetAllController;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: christine
 * Date: 1/21/19 2:32 PM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LetterGetAllController.class)
@ActiveProfiles("test")
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LetterService service;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private List<Letter> letters = new LinkedList<>();
    private Letter letter1 = new Letter();

    private String json = "{\"requestCode\":0}";
    private String comment ="this my comment";

    @Before
    public void setup() {
        letter1.setComment(comment);
        letters.add(letter1);
        letters.add(new Letter());
     }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(service.getLetters()).thenReturn(letters);
        this.mockMvc.perform(post("/get_letters/")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(comment)));

        verify(service).getLetters();
    }
}