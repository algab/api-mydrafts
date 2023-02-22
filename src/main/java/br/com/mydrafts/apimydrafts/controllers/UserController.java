package br.com.mydrafts.apimydrafts.controllers;

import br.com.mydrafts.apimydrafts.annotations.AuthorizeParam;
import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.FavoriteDTO;
import br.com.mydrafts.apimydrafts.dto.UserDTO;
import br.com.mydrafts.apimydrafts.dto.UserFormDTO;
import br.com.mydrafts.apimydrafts.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserFormDTO body) {
        long start = System.currentTimeMillis();
        log.info("UserController.saveUser - Start - Input: firstName [{}], firstName [{}], email [{}], gender [{}]",
            body.getFirstName(), body.getLastName(), body.getEmail(), body.getGender()
        );

        UserDTO user = this.service.saveUser(body);

        log.info("UserController.saveUser - End - Input: firstName [{}], firstName [{}], email [{}], gender [{}] - time: {} ms",
            body.getFirstName(), body.getLastName(), body.getEmail(), body.getGender(), System.currentTimeMillis() - start
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping(path = "/{id}")
    @AuthorizeParam
    public ResponseEntity<UserDTO> searchUser(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        log.info("UserController.searchUser - Start - Input: id [{}]", id);

        UserDTO user = this.service.searchUser(id);

        log.info("UserController.searchUser - End - Input: id [{}] - Output: [{}] - time: {} ms",
            id, user, System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/drafts")
    @AuthorizeParam
    public ResponseEntity<Page<DraftDTO>> getDrafts(@PathVariable("id") String id, @PageableDefault Pageable page) {
        long start = System.currentTimeMillis();
        log.info("UserController.getDrafts - Start - Input: id [{}], page [{}]", id, page);

        Page<DraftDTO> content = this.service.getDrafts(page, id);

        log.info("UserController.getDrafts - End - Input: id [{}], page [{}] - Output: [{}] - time: {} ms",
            id, page, content, System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}/favorites")
    @AuthorizeParam
    public ResponseEntity<Page<FavoriteDTO>> getFavorites(@PathVariable("id") String id, @PageableDefault Pageable page) {
        long start = System.currentTimeMillis();
        log.info("UserController.getFavorites - Start - Input: id [{}], page [{}]", id, page);

        Page<FavoriteDTO> content = this.service.getFavorites(page, id);

        log.info("UserController.getFavorites - End - Input: id [{}], page [{}] - Output: [{}] - time: {} ms",
            id, page, content, System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(content);
    }

    @PutMapping(path = "/{id}")
    @AuthorizeParam
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserFormDTO body) {
        long start = System.currentTimeMillis();
        log.info("UserController.updateUser - Start - Input: id [{}], firstName [{}], firstName [{}], email [{}], gender [{}]",
            id, body.getFirstName(), body.getLastName(), body.getEmail(), body.getGender()
        );

        UserDTO user = this.service.updateUser(id, body);

        log.info("UserController.updateUser - End - Input: id [{}], firstName [{}], firstName [{}], email [{}], gender [{}] - time: {} ms",
            id, body.getFirstName(), body.getLastName(), body.getEmail(), body.getGender(), System.currentTimeMillis() - start
        );
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{id}")
    @AuthorizeParam
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        long start = System.currentTimeMillis();
        log.info("UserController.deleteUser - Start - Input: id [{}]", id);

        this.service.deleteUser(id);

        log.info("UserController.deleteUser - End - Input: id [{}] - time: {} ms", id, System.currentTimeMillis() - start);
        return ResponseEntity.noContent().build();
    }

}
