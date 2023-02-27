package tech.leftii.animaltracker;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.leftii.animaltracker.config.RsaKeyProperties;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Application Entry Point
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableJpaRepositories
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AnimalTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalTrackerApplication.class, args);
    }

    /**
     * Command Line Runner
     * Use this to run commands on startup within the Spring Application
     * Can be used to create/persist test objects (data.sql already creates test objects for db)
     *
     * @return Arguments to be run in command line on start of application
     */
    @Bean
    public CommandLineRunner runner() throws IOException {
        return args -> {
        };
    }

    /**
     * Start internal H2 server, so we can query the DB from IDE
     *
     * @return H2 Server instance
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

}
