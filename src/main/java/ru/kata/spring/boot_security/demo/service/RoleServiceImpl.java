package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;
    
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Role getRoleByTitle(String title) {
        Optional<Role> optionalRole =  roleRepository.findByTitle(title);
        if (optionalRole.isEmpty()) {
            throw new EntityNotFoundException(String.format("Role '%s' not found", title));
        }
        return optionalRole.get();
    }
    
    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }
}
