package one.tsv.shortinatorbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "link")
public class Link {

    @Id
    @Column(length = 10)
    private String id;

    @Column
    private String link;

    @Column(nullable = false)
    private Date creationDate;

    public Link() {

    }

    public Link(String id, String link) {
        this.id = id;
        this.link = link;
        this.creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}