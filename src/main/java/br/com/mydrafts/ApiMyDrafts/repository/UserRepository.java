package br.com.mydrafts.ApiMyDrafts.repository;

import br.com.mydrafts.ApiMyDrafts.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsByEmail(String email);

}
