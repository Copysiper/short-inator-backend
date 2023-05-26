package one.tsv.shortinatorbackend.controller;

import one.tsv.shortinatorbackend.model.Link;
import one.tsv.shortinatorbackend.repository.ApiKeyRepository;
import one.tsv.shortinatorbackend.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/l")
public class LinkController {
    private static final Logger log = LoggerFactory.getLogger(LinkController.class);
    private final LinkRepository linkRepository;
    private final ApiKeyRepository keyRepository;

    private static final Random random = new Random(System.currentTimeMillis());

    static final int defaultIdLength = 10; //Length for randomly generated strings of shortened links
    static final String freeKey = "free"; //Api key for standard usage (usually for free, id is generated randomly)

    static final boolean isFreeAllowed = true; //Check if free access is allowed

    @Autowired
    public LinkController(LinkRepository linkRepository, ApiKeyRepository keyRepository) {
        this.linkRepository = linkRepository;
        this.keyRepository = keyRepository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/info/{id}")
    public ResponseEntity<Link> getRedirectLink(@PathVariable String id) {
        return ResponseEntity.of(linkRepository.findById(id));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<?> getLink(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        String target = linkRepository.existsById(id) ? linkRepository.findById(id).get().getLink() : "/404";
        headers.add("Location", target);
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<?> createLink(@RequestBody Link link, @RequestParam("key") String apiKey) throws URISyntaxException {
        if ((!isFreeAllowed) && apiKey.equals(freeKey)) {
            return ResponseEntity.badRequest().body("Cannot use free key for this operation");
        }
        else if (apiKey.equals(freeKey)) {
            Link savedLink = linkRepository.save(new Link(generateRandomId(defaultIdLength), link.getLink()));
            return ResponseEntity.created(new URI("/l/info/" + savedLink.getId())).body(savedLink);
        }
        else if (keyRepository.existsById(UUID.fromString(apiKey))) {
            Link savedLink = linkRepository.save(new Link(link.getId(), link.getLink()));
            return ResponseEntity.created(new URI("/l/info/" + savedLink.getId())).body(savedLink);
        }
        else {
            return ResponseEntity.badRequest().body("It looks like api key provided is invalid");
        }
    }

    //Almost all letters are used for generation except those which are hard to distinguish like l and 1 or 0 and O
    public static String generateRandomId(int length) {
        String chars = "abcdeghijkmnpqrsuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ23456789";
        char[] buf = new char[length];
        for (int i = 0; i < length; i++)
            buf[i] = chars.charAt(random.nextInt(chars.length()));
        return new String(buf);
    }
}
