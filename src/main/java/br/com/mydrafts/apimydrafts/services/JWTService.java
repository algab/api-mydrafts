package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.UserDTO;

public interface JWTService {

    String getIdByToken();

    String generateToken(UserDTO user);

    boolean validateToken();

}
