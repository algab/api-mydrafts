package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.documents.Favorite;
import br.com.mydrafts.apimydrafts.documents.Production;
import br.com.mydrafts.apimydrafts.documents.User;
import br.com.mydrafts.apimydrafts.dto.*;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.FavoriteRepository;
import br.com.mydrafts.apimydrafts.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteRepository favoriteRepository;

    private UserRepository userRepository;

    private ProductionService productionService;

    private ModelMapper mapper;

    private static final String MESSAGE_FAVORITE_CONFLICT = "Favorite already registered";
    private static final String MESSAGE_USER_NOT_FOUND = "User not found";
    private static final String MESSAGE_FAVORITE_NOT_FOUND = "Favorite not found";

    @Override
    public FavoriteDTO save(FavoriteFormDTO body) {
        log.info("FavoriteServiceImpl.save - Start - Input: body {}", body);

        User user = this.userRepository.findById(body.getUserID())
            .orElseThrow(() -> {
                log.error("FavoriteServiceImpl.save - Error: {}", MESSAGE_USER_NOT_FOUND);
                return new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), MESSAGE_USER_NOT_FOUND);
            });

        Favorite favorite = setDataFavorite(body, user);
        FavoriteDTO favoriteResult = mapper.map(this.favoriteRepository.save(favorite), FavoriteDTO.class);

        log.info("FavoriteServiceImpl.save - End - Input: body {} - Output: {}", body, favoriteResult);
        return favoriteResult;
    }

    @Override
    public void delete(String id) {
        log.info("FavoriteServiceImpl.delete - Start - Input: id {}", id);

        Favorite favorite = this.favoriteRepository.findById(id)
            .orElseThrow(() -> {
                log.error("FavoriteServiceImpl.delete - Error: {}", MESSAGE_FAVORITE_NOT_FOUND);
                return new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), MESSAGE_FAVORITE_NOT_FOUND);
            });

        log.info("FavoriteServiceImpl.delete - End - Input: id {}", id);
        this.favoriteRepository.delete(favorite);
    }

    private Favorite setDataFavorite(FavoriteFormDTO body, User user) {
        Favorite favorite = Favorite.builder().build();
        favorite.setUser(user);
        Production production = this.productionService.searchByTmdbID(body.getTmdbID());
        if (production != null) {
            if (this.favoriteRepository.existsByUserAndProduction(user, production)) {
                log.error("FavoriteServiceImpl.setDataFavorite - Error: {}", MESSAGE_FAVORITE_CONFLICT);
                throw new BusinessException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), MESSAGE_FAVORITE_CONFLICT);
            }
            favorite.setProduction(production);
        } else {
            Production productionResponse = this.productionService.mountProduction(body.getTmdbID(), body.getMedia(), null);
            favorite.setProduction(productionResponse);
        }
        return favorite;
    }

}
