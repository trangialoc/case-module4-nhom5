package com.codegym.vn.controller.admin;

import com.codegym.vn.model.Role;
import com.codegym.vn.service.interfaceImpl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/roles")
public class RoleController {
    @Autowired
    private IRoleService iRoleService;

    @GetMapping
    public ResponseEntity<?> showRole() {
        Iterable<Role> roles = iRoleService.findAll();
        if (!roles.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Role> role = iRoleService.findById(id);
        if (!role.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iRoleService.delete(id);
        return new ResponseEntity<>(role.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> createProduct(@RequestBody Role role) {
        Role role1 = iRoleService.save(role);
        return new ResponseEntity<>(role1, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Role> editProduct(@RequestBody Role role, @PathVariable("id") Long id) {
        Optional<Role> roleOptional = iRoleService.findById(id);
        if (!roleOptional.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        role.setId(roleOptional.get().getId());
        role = iRoleService.save(role);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
