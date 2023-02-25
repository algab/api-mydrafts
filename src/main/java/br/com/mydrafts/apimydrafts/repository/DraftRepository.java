package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.documents.DraftDocument;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.documents.UserDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DraftRepository extends MongoRepository<DraftDocument, String> {

    boolean existsByUserAndProduction(UserDocument user, ProductionDocument production);

    Page<DraftDocument> findByUser(UserDocument user, Pageable page);

}
