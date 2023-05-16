package one.tsv.shortinatorbackend.repository;

import one.tsv.shortinatorbackend.model.ApiKey;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ApiKeyRepository extends CrudRepository<ApiKey, UUID> {

}