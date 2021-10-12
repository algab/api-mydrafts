package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBProxy;
import br.com.mydrafts.ApiMyDrafts.documents.Favorite;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.*;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.FavoriteRepository;
import br.com.mydrafts.ApiMyDrafts.repository.ProductionRepository;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TMDBProxy tmdbProxy;

    @Autowired
    private ModelMapper mapper;

    @Override
    public FavoriteDTO save(FavoriteFormDTO body) {
        Favorite favorite = Favorite.builder().build();
        User user = this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "User not found"));
        favorite.setUser(user);
        Optional<Production> production = this.productionRepository.findByTmdbID(body.getTmdbID());
        if (production.isPresent()) {
            if (this.favoriteRepository.existsByUserAndProduction(user, production.get())) {
                throw new BusinessException(409, "CONFLICT", "Favorite already registered");
            }
            favorite.setProduction(production.get());
        } else {
            Production saveProduction = this.productionRepository.save(this.tmdbProxy.findProduction(body.getMedia(), body.getTmdbID()));
            favorite.setProduction(saveProduction);
        }
        return mapper.map(this.favoriteRepository.save(favorite), FavoriteDTO.class);
    }

    @Override
    public Page<FavoriteDTO> getFavorites(Pageable page, String userID) {
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "User not found"));
        Page<Favorite> favorites = this.favoriteRepository.findByUser(user, page);
        List<FavoriteDTO> content = favorites.getContent().stream()
                .map(favorite -> mapper.map(favorite, FavoriteDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content, page, favorites.getTotalElements());
    }

    @Override
    public void delete(String id) {
        Favorite favorite = this.favoriteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "Favorite not found"));
        this.favoriteRepository.delete(favorite);
    }

}
