package one.tsv.shortinatorbackend.debug;

import one.tsv.shortinatorbackend.model.ApiKey;
import one.tsv.shortinatorbackend.model.Link;
import one.tsv.shortinatorbackend.repository.ApiKeyRepository;
import one.tsv.shortinatorbackend.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//This thing exists for debug purposes and should not be enabled in production environment
@Configuration
public class DebugLoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(DebugLoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(LinkRepository linkRepository, ApiKeyRepository apiKeyRepository) {

        return args -> {
            log.info("Preloading pre-created link: " + linkRepository.save(new Link("123", "https://example.com")));
            ApiKey key = apiKeyRepository.save(new ApiKey());
            log.info("Preloading test api key: " + key.getApiKey());
        };
    }
}