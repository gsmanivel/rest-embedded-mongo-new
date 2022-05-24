import com.intuit.karate.junit5.Karate;
import com.rest.mongo.UserApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@ActiveProfiles("test")
@SpringBootTest(classes = UserApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("Karate-Integration Test")
public class TestRunner {

    @Karate.Test
    Karate invokeHelloFeatureTest() throws IOException {
        return new Karate().run("hello").relativeTo(getClass());
    }
}
