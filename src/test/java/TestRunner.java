import com.intuit.karate.junit5.Karate;
import com.rest.mongo.UserApplication;
import com.rest.mongo.entity.User;
import com.rest.mongo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.when;


@SpringBootTest(classes = UserApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("Karate-Integration Test")
public class TestRunner {

    @MockBean
    public UserRepository userRepository;



    @Karate.Test
    Karate invokeHelloFeatureTest() throws IOException {
        User user = new User("1234","user-1");
        when(userRepository.save(user)).thenReturn(user);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        return new Karate().run("hello").relativeTo(getClass());
    }
}