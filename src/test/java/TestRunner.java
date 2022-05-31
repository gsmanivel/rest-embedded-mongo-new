import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.rest.mongo.UserApplication;
import com.rest.mongo.entity.User;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DisplayName("Karate-Integration Test")
public class TestRunner {
    private static MongoServer server;
    private static MongoClient client;
    private static MongoCollection collection;
    private static MongoTemplate mongoTemplate;
    private static final String LOCAL_HOST = "localhost";

    @BeforeEach
    void setUp() {
        createMongoClient();
        createMongoCollection();
        createMongoTemplate();
    }

    private static void createMongoClient() {
        server = new MongoServer(new MemoryBackend());
        server.bind(LOCAL_HOST, 27117);
        final MongoClientSettings settings = getMongoClientSettings();
        client = MongoClients.create(settings);
    }
    private static MongoClientSettings getMongoClientSettings() {
        return MongoClientSettings.builder().applyToClusterSettings(
                builder ->  builder.hosts(Collections.singletonList(new  ServerAddress(LOCAL_HOST)))).build();
    }

    private static void createMongoCollection() {
      collection =  client.getDatabase("testdb").getCollection("testcollection");
    }

    private static void createMongoTemplate() {
        SimpleMongoClientDatabaseFactory mongoDbFactory = new SimpleMongoClientDatabaseFactory(client,  "testdb");
        mongoTemplate = new MongoTemplate(mongoDbFactory, getMongoConverter(mongoDbFactory));
    }

    private static MongoConverter getMongoConverter(MongoDatabaseFactory mongoDbFactory)
    {
        MongoMappingContext mappingContext = new MongoMappingContext();
        mappingContext.setAutoIndexCreation(true);
        mappingContext.afterPropertiesSet();
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        return new MappingMongoConverter(dbRefResolver, mappingContext);
    }

    @AfterEach
    void tearDown() {
        client.close();
        server.shutdown();
    }

    @Test
    public void testMongoTemplate() {
        User user = new User();
       user.setUserName("thomos");
        mongoTemplate.save(user, "data");
        List<User> data = mongoTemplate.findAll(User.class, "data");
        assertEquals(0,data.size());
    }



//    @Karate.Test
//    Karate invokeHelloFeatureTest() throws IOException {
//
//        return new Karate().run("hello").relativeTo(getClass());
//    }
}