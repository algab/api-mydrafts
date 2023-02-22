package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.LoginDTO;
import br.com.mydrafts.apimydrafts.dto.LoginFormDTO;
import br.com.mydrafts.apimydrafts.services.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/login")
@AllArgsConstructor
public class LoginController {

    private LoginService service;

    @PostMapping
    public ResponseEntity<LoginDTO> login(@RequestBody @Valid LoginFormDTO body) {
        long start = System.currentTimeMillis();
        log.info("LoginController.login - Start - Input: email [{}]", body.getEmail());

        LoginDTO user = service.login(body);

        log.info("LoginController.login - End - Input: email [{}] - time: {} ms",
            body.getEmail(), System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(user);
    }

}
