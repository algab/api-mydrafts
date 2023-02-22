package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.documents.User;
import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.EMAIL_NOT_FOUND;
import static br.com.mydrafts.apimydrafts.constants.MyDraftsMessage.PASSWORD_INCORRECT;

@Slf4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private UserRepository repository;

    private JWTService jwtService;

    private ModelMapper mapper;

    @Override
    public LoginDTO login(LoginFormDTO login) {
        User user = repository.findByEmail(login.getEmail())
            .orElseThrow(() -> {
                log.error("LoginServiceImpl.login - Error: {}", EMAIL_NOT_FOUND);
                return new BusinessException(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    EMAIL_NOT_FOUND
                );
            });
        if (new BCryptPasswordEncoder().matches(login.getPassword(), user.getPassword())) {
            UserDTO userResponse = mapper.map(user, UserDTO.class);
            String token = jwtService.generateToken(userResponse);
            return LoginDTO.builder().token(token).user(userResponse).build();
        }
        log.error("LoginServiceImpl.login - Error: {}", PASSWORD_INCORRECT);
        throw new BusinessException(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            PASSWORD_INCORRECT
        );
    }

}
