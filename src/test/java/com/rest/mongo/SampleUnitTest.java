package com.rest.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.rest.mongo.entity.User;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.jupiter.api.*;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.test.context.ActiveProfiles;
import java.net.InetSocketAddress;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DisplayName("Karate-Integration Test")
public class SampleUnitTest {
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
        InetSocketAddress serverAddress = server.bind();

        ConnectionString connectionString = new ConnectionString(
                "mongodb://" + serverAddress.getHostName() + ":" + serverAddress.getPort() + "/user-test");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        client = MongoClients.create(settings);
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
        assertEquals(1,data.size());
    }
}