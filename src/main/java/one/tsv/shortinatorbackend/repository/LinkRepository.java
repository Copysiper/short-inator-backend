package one.tsv.shortinatorbackend.repository;

import one.tsv.shortinatorbackend.model.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, String> {
}