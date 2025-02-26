package com.example.myevent_be.service;

import com.example.myevent_be.entity.Role;
import com.example.myevent_be.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getOne(String id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Role not found");
        }
        return role.get();
    }

}
