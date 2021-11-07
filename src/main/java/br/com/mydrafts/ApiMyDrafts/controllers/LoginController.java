package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.LoginDTO;
import br.com.mydrafts.ApiMyDrafts.dto.LoginFormDTO;
import br.com.mydrafts.ApiMyDrafts.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/login")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping
    public ResponseEntity<LoginDTO> login(@RequestBody @Valid LoginFormDTO form) {
        log.info("LoginController.login - Start - Input: email {}", form.getEmail());

        LoginDTO user = service.login(form);

        log.info("LoginController.login - End - Output: user {}", user);
        return ResponseEntity.ok(user);
    }

}
