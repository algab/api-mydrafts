package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.documents.FavoriteDocument;
import br.com.mydrafts.apimydrafts.documents.ProductionDocument;
import br.com.mydrafts.apimydrafts.documents.UserDocument;
import br.com.mydrafts.apimydrafts.dto.*;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.FAVORITE_CONFLICT;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.FAVORITE_NOT_FOUND;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.USER_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteRepository favoriteRepository;

    private UserRepository userRepository;

    private ProductionService productionService;

    private ModelMapper mapper;

    @Override
    public FavoriteDTO save(FavoriteFormDTO body) {
        UserDocument user = this.userRepository.findById(body.getUserID())
            .orElseThrow(() -> {
                log.error("FavoriteServiceImpl.save - Error: {}", USER_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.toString(),
                    USER_NOT_FOUND
                );
            });
        FavoriteDTO favorite = mapper.map(this.favoriteRepository.save(setDataFavorite(body, user)), FavoriteDTO.class);
        log.info("FavoriteServiceImpl.save - Favorite saved - favorite: [{}]", favorite);
        return favorite;
    }

    @Override
    public void delete(String id) {
        FavoriteDocument favorite = this.favoriteRepository.findById(id)
            .orElseThrow(() -> {
                log.error("FavoriteServiceImpl.delete - Error: {}", FAVORITE_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.toString(),
                    FAVORITE_NOT_FOUND
                );
            });
        log.info("FavoriteServiceImpl.delete - Favorite removed - id: [{}]", id);
        this.favoriteRepository.delete(favorite);
    }

    private FavoriteDocument setDataFavorite(FavoriteFormDTO body, UserDocument user) {
        FavoriteDocument favorite = FavoriteDocument.builder().user(user).build();
        Optional<ProductionDocument> production = this.productionService.searchProduction(body.getTmdbID(), body.getMedia());
        if (production.isPresent()) {
            if (this.favoriteRepository.existsByUserAndProduction(user, production.get())) {
                log.error("FavoriteServiceImpl.setDataFavorite - Error: {}", FAVORITE_CONFLICT);
                throw new BusinessException(
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.getReasonPhrase(),
                    FAVORITE_CONFLICT
                );
            }
            favorite.setProduction(production.get());
        } else {
            ProductionDocument productionResponse = this.productionService.mountProduction(body.getTmdbID(), body.getMedia());
            favorite.setProduction(productionResponse);
        }
        return favorite;
    }

}
