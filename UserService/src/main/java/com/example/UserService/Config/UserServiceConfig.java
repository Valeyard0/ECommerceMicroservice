package com.example.UserService.Config;

import com.example.UserService.Entity.Role;
import com.example.UserService.Repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository) {
        return args -> {
            Role userRole = roleRepository.findByRoleName("ROLE_USER")
                    .orElseGet(() -> {
                        Role newUserRole = new Role("ROLE_USER");
                        return roleRepository.save(newUserRole);
                    });

            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role newAdminRole = new Role("ROLE_ADMIN");
                        return roleRepository.save(newAdminRole);
                    });

        };
    }
}