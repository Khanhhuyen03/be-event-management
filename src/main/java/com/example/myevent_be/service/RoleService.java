//package com.example.myevent_be.service;
//
//import com.example.myevent_be.entity.Role;
//import com.example.myevent_be.repository.RoleRepository;
//import org.hibernate.query.Page;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.awt.print.Pageable;
//import java.util.Optional;
//
//import static org.springframework.http.HttpStatus.NOT_FOUND;
//
//@Service
//public class RoleService {
//    @Autowired
//    private RoleRepository roleRepository;
//
//    public Role getOne(Long id) {
//        Optional<Role> role = roleRepository.findById(id);
//        if (role.isEmpty()) {
//            throw new ResponseStatusException(NOT_FOUND, "Role not found");
//        }
//        return role.get();
//    }
//
//    public Page<Role> getAll(QueryFilterDto queryParams) {
//        Pageable pageable = PageRequest.of(queryParams.getPage(), queryParams.getSize());
//        Specification<Role> spec = QuerySpecificationBuilder.build(queryParams);
//        return roleRepository.findAll(spec, pageable);
//    }
//}
