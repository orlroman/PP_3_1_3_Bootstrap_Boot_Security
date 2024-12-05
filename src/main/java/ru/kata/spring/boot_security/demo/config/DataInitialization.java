package ru.kata.spring.boot_security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Component
public class DataInitialization {
    
    private final UserService userService;
    private final RoleService roleService;
    
    @Autowired
    public DataInitialization(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    @PostConstruct
    public void init() {
        
        Role adminRole;
        try {
            adminRole = roleService.getRoleByTitle("ROLE_ADMIN");
        } catch (EntityNotFoundException e) {
            adminRole = new Role("ROLE_ADMIN");
            roleService.save(adminRole);
        }
        
        Role userRole;
        try {
            userRole = roleService.getRoleByTitle("ROLE_USER");
        } catch (EntityNotFoundException e) {
            userRole = new Role("ROLE_USER");
            roleService.save(userRole);
        }
        

        User admin;
        try {
            userService.getUserByUsername("admin@example.com");
        } catch (UsernameNotFoundException e) {
            admin = new User("Admin", "Admin", 30,
                    "admin@example.com", "admin", Set.of(adminRole, userRole));
            userService.save(admin);
        }
        
        User user;
        try {
            userService.getUserByUsername("user@example.com");
        } catch (UsernameNotFoundException e) {
            user = new User("User", "User", 35,
                    "user@example.com", "user", Set.of(userRole));
            userService.save(user);
        }
        
    }
}
