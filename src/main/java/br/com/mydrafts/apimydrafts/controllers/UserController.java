package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import br.com.mydrafts.apimydrafts.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserFormDTO body) {
        log.info("UserController.saveUser - Start - Input: name {}, email {}", body.getName(), body.getEmail());

        UserDTO user = this.service.saveUser(body);

        log.info("UserController.saveUser - End - - Input: name {}, email {} - Output: {}", body.getName(), body.getEmail(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PreAuthorize("#id == authentication.principal")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> searchUser(@PathVariable("id") String id) {
        log.info("UserController.searchUser - Start - Input: id {}", id);

        UserDTO user = this.service.searchUser(id);

        log.info("UserController.searchUser - End - Input: id {} - Output: {}", id, user);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("#id == authentication.principal")
    @GetMapping("/{id}/drafts")
    public ResponseEntity<Page<DraftDTO>> getDrafts(@PathVariable("id") String id, @PageableDefault Pageable page) {
        log.info("UserController.getDrafts - Start - Input: id {}, page {}", id, page);

        Page<DraftDTO> content = this.service.getDrafts(page, id);

        log.info("UserController.getDrafts - End - Input: id {}, page {} Output: {}", id, page, content);
        return ResponseEntity.ok(content);
    }

    @PreAuthorize("#id == authentication.principal")
    @GetMapping("/{id}/favorites")
    public ResponseEntity<Page<FavoriteDTO>> getFavorites(@PathVariable("id") String id, @PageableDefault Pageable page) {
        log.info("UserController.getFavorites - Start - Input: id {}, page {}", id, page);

        Page<FavoriteDTO> content = this.service.getFavorites(page, id);

        log.info("UserController.getFavorites - End - Input: id {}, page {} - Output: {}", id, page, content);
        return ResponseEntity.ok(content);
    }

    @PreAuthorize("#id == authentication.principal")
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserFormDTO body) {
        log.info("UserController.updateUser - Start - Input: id {}", id);

        UserDTO user = this.service.updateUser(id, body);

        log.info("UserController.updateUser - End - Input: id {} - Output: {}", id, user);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("#id == authentication.principal")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        log.info("UserController.deleteUser - Start - Input: id {}", id);

        this.service.deleteUser(id);

        log.info("UserController.deleteUser - End - Input: id {}", id);
        return ResponseEntity.noContent().build();
    }

}
