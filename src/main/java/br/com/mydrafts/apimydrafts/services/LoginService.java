package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;

public interface LoginService {

    LoginDTO login(LoginFormDTO login);

}
