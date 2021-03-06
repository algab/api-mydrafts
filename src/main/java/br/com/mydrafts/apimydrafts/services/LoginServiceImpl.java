package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.documents.User;
import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.exceptions.BusinessException;
import br.com.mydrafts.apimydrafts.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Value("${secret.jwt}")
    private String secret;

    private static final String MESSAGE_PASSWORD_INCORRECT = "Password incorrect";
    private static final String MESSAGE_EMAIL_NOT_FOUND = "Email not found";

    @Override
    public LoginDTO login(LoginFormDTO login) {
        log.info("LoginServiceImpl.login - Start - Input: email {}", login.getEmail());

        User user = repository.findByEmail(login.getEmail())
                .orElseThrow(() -> {
                    log.error("LoginServiceImpl.login - Error: {}", MESSAGE_EMAIL_NOT_FOUND);
                    return new BusinessException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), MESSAGE_EMAIL_NOT_FOUND);
                });
        if (new BCryptPasswordEncoder().matches(login.getPassword(), user.getPassword())) {
            UserDTO userResponse = mapper.map(user, UserDTO.class);
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            String token = Jwts.builder().setId(userResponse.getId()).setSubject(userResponse.getName())
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(LocalDateTime.now().plusMonths(6L).atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
            LoginDTO loginResponse = LoginDTO.builder().token(token).user(userResponse).build();
            log.info("LoginServiceImpl.login - End - Input: email {} - Output: {}", login.getEmail(), loginResponse);
            return loginResponse;
        }
        log.error("LoginServiceImpl.login - Error: {}", MESSAGE_PASSWORD_INCORRECT);
        throw new BusinessException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), MESSAGE_PASSWORD_INCORRECT);
    }

}
