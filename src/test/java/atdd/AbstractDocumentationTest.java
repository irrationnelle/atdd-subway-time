package atdd;

import atdd.path.auth.JwtTokenProvider;
import atdd.path.auth.SignInInterceptor;
import atdd.path.auth.SignInResolver;
import atdd.path.configuration.WebMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(WebMvcTestConfig.class)
public class AbstractDocumentationTest {
    @MockBean
    public SignInInterceptor signInInterceptor;
    @MockBean
    public SignInResolver signInResolver;
    @MockBean
    public WebMvcConfig webMvcConfig;
    @MockBean
    public JwtTokenProvider jwtTokenProvider;

    public MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
}
