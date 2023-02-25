package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDocument, String> {

    boolean existsByEmail(String email);

    Optional<UserDocument> findByEmail(String email);

}
