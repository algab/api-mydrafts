package br.com.mydrafts.ApiMyDrafts.controllers;

import br.com.mydrafts.ApiMyDrafts.dto.UserDTO;
import br.com.mydrafts.ApiMyDrafts.dto.UserFormDTO;
import br.com.mydrafts.ApiMyDrafts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> searchUser(@PathVariable("id") String id) {
        UserDTO user = this.service.searchUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserFormDTO body) {
        UserDTO user = this.service.updateUser(id, body);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        this.service.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
