package one.tsv.shortinatorbackend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "apikey")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID apiKey;

    @Column(nullable = false)
    private Date creationDate;

    public ApiKey() {
        this.creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public UUID getApiKey() {
        return apiKey;
    }

}