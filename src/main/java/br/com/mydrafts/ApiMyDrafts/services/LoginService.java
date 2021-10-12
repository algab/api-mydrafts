package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.dto.LoginFormDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;

public interface LoginService {

    UserDTO login(LoginFormDTO login);

}
