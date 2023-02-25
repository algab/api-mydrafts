package br.com.mydrafts.apimydrafts.repository;

import br.com.mydrafts.apimydrafts.documents.FavoriteDocument;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.documents.UserDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<FavoriteDocument, String> {

    boolean existsByUserAndProduction(UserDocument user, ProductionDocument production);

    Page<FavoriteDocument> findByUser(UserDocument user, Pageable page);

}
