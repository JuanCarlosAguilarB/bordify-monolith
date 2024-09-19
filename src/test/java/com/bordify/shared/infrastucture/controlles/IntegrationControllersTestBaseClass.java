package com.bordify.shared.infrastucture.controlles;

import com.bordify.controllers.auth.AuthController;
import com.bordify.controllers.auth.LoginRequest;
import com.bordify.controllers.auth.ResponseDTO;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.bordify.persistence.models.UserFactory.getRandomUser;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
abstract public class IntegrationControllersTestBaseClass implements IntegrationTestBaseClass{


    private final User user = getRandomUser();
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthController authPostController;
    @Autowired
    private PasswordEncoder securityService;
    private Boolean userWasPersisted = false;

    public User user() {
        return user;
    }

    // TODO: i need delete this user of db when all tets were execute
    public User createRandomPersistentUser() {

        if (!userWasPersisted) {

            String oldPassword = user.getPassword();

            user.setPassword(securityService.encode(oldPassword));
            repository.save(user);

            // recovery raw password
            user.setPassword(oldPassword);
            userWasPersisted = true;
        }

        return user;
    }

    private String token() throws Exception {

        User user = createRandomPersistentUser();

        LoginRequest authenticateBody = LoginRequest.builder()
                .password(user.getPassword())
                .username(user.getUsername())
                .build();

        ResponseEntity<?> authentication = authPostController.authenticateUser(authenticateBody);

        ResponseDTO authToken = (ResponseDTO) authentication.getBody();

        return authToken.getToken();
    }


    private MockHttpServletRequestBuilder mockRequest(
            HttpMethod method,
            String url,
            Object body,
            boolean needsAuthentication
    ) throws Exception {

        MockHttpServletRequestBuilder requestResult = MockMvcRequestBuilders.request(method, url)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(APPLICATION_JSON)
                .characterEncoding("utf-8");

        if (needsAuthentication) {
            requestResult.header("Authorization", "Bearer " + token());
        }

        return requestResult;
    }

    public ResultActions assertRequestWithBody(
            HttpMethod method,
            String url,
            Object body,
            int expectedStatusCode,
            String expectedResponse,
            boolean needsAuthentication
    ) throws Exception {

        ResultMatcher bodyEspexted = expectedResponse.isEmpty() ? content().string("") : content().json(expectedResponse);

        return mockMvc.perform(mockRequest(method, url, body, needsAuthentication))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(bodyEspexted);
//                .andReturn();
    }

    public ResultActions assertRequestWithBody(
            HttpMethod method,
            String url,
            Object body,
            int expectedStatusCode,
            boolean needsAuthentication
    ) throws Exception {


        return mockMvc.perform(mockRequest(method, url, body, needsAuthentication))
                .andExpect(status().is(expectedStatusCode));
//                .andReturn();

    }


    public ResultActions assertRequest(
            HttpMethod method, String url,
            int expectedStatusCode,
            String expectedResponse,
            boolean needsAuthentication
    ) throws Exception {

        ResultMatcher bodyEspexted = expectedResponse.isEmpty() ? content().string("") : content().json(expectedResponse);

        return mockMvc.perform(mockRequest(method, url, null, needsAuthentication))
                .andExpect(status().is(expectedStatusCode))
                .andExpect(bodyEspexted);
    }

    public ResultActions assertRequest(HttpMethod method, String url, int expectedStatusCode, boolean needsAuthentication) throws Exception {

        return mockMvc.perform(mockRequest(method, url, null, needsAuthentication))
                .andExpect(status().is(expectedStatusCode));
    }

    public String buildUrl(String uri, String value) {

        int startIndex = uri.indexOf("{");
        if (startIndex == -1) {
            return uri;
        }

        int endIndex = uri.indexOf("}", startIndex);
        if (endIndex == -1) {
            return uri;
        }

        return uri.substring(0, startIndex) + value + uri.substring(endIndex + 1);
    }

}
