package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteFormDTO;

public interface FavoriteService {

    FavoriteDTO save(FavoriteFormDTO body);

    void delete(String id);

}
