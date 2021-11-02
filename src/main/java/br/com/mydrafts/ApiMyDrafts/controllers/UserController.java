package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.FavoriteDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import br.com.mydrafts.ApiMyDrafts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserFormDTO body) {
        UserDTO user = this.service.saveUser(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PreAuthorize("#id == authentication.principal")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> searchUser(@PathVariable("id") String id) {
        UserDTO user = this.service.searchUser(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("#id == authentication.principal")
    @GetMapping("/{id}/drafts")
    public ResponseEntity<Page<DraftDTO>> getDrafts(@PathVariable("id") String id, @PageableDefault Pageable page) {
        Page<DraftDTO> pageDraft = this.service.getDrafts(page, id);
        return ResponseEntity.ok(pageDraft);
    }

    @PreAuthorize("#id == authentication.principal")
    @GetMapping("/{id}/favorites")
    public ResponseEntity<Page<FavoriteDTO>> getFavorites(@PathVariable("id") String id, @PageableDefault Pageable page) {
        Page<FavoriteDTO> content = this.service.getFavorites(page, id);
        return ResponseEntity.ok(content);
    }

    @PreAuthorize("#id == authentication.principal")
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserFormDTO body) {
        UserDTO user = this.service.updateUser(id, body);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("#id == authentication.principal")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        this.service.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
