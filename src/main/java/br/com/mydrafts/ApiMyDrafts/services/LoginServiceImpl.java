package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.LoginDTO;
import br.com.mydrafts.ApiMyDrafts.dto.LoginFormDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Value("${secret-jwt}")
    private String secret;

    private static final String BAD_REQUEST = "BAD REQUEST";
    private static final String NOT_FOUND = "NOT FOUND";
    private static final Integer STATUS_BAD_REQUEST = 400;
    private static final Integer STATUS_NOT_FOUND = 404;
    private static final String MESSAGE_PASSWORD_INCORRECT = "Password incorrect";
    private static final String MESSAGE_EMAIL_NOT_FOUND = "Email not found";

    @Override
    public LoginDTO login(LoginFormDTO login) {
        User user = repository.findByEmail(login.getEmail())
                .orElseThrow(() -> new BusinessException(STATUS_NOT_FOUND, NOT_FOUND, MESSAGE_EMAIL_NOT_FOUND));
        if (new BCryptPasswordEncoder().matches(login.getPassword(), user.getPassword())) {
            UserDTO userResponse = mapper.map(user, UserDTO.class);
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            String token = Jwts.builder().setId(userResponse.getId()).setSubject(userResponse.getName()).signWith(key, SignatureAlgorithm.HS256).compact();
            return LoginDTO.builder().token(token).user(userResponse).build();
        }
        throw new BusinessException(STATUS_BAD_REQUEST, BAD_REQUEST, MESSAGE_PASSWORD_INCORRECT);
    }

}
