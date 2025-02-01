package com.cenfo.tech.task1.config;

import com.cenfo.tech.task1.entity.Role;
import com.cenfo.tech.task1.entity.RoleEnum;
import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.repository.IRoleRepository;
import com.cenfo.tech.task1.repository.IUserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(IRoleRepository roleRepository, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createRolesIfNotExist();
        this.createDefaultUsers();
    }

    private void createRolesIfNotExist() {
        if (roleRepository.findByName(RoleEnum.USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(RoleEnum.USER);
            userRole.setDescription(RoleEnum.USER.name());
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName(RoleEnum.SUPER_ADMIN).isEmpty()) {
            Role superAdminRole = new Role();
            superAdminRole.setName(RoleEnum.SUPER_ADMIN);
            superAdminRole.setDescription(RoleEnum.SUPER_ADMIN.name());
            roleRepository.save(superAdminRole);
        }
    }

    private void createDefaultUsers() {
        createSuperAdmin();
        createUser();
    }

    private void createUser() {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        String USER_EMAIL = "user1@gmail.com";
        Optional<User> optionalUser = userRepository.findByEmail(USER_EMAIL);

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail(USER_EMAIL);
        user1.setPassword(passwordEncoder.encode("user123"));
        user1.setRole(optionalRole.get());
        userRepository.save(user1);
    }

    private void createSuperAdmin() {

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        String SUPER_ADMIN_EMAIL = "super_admin@gmail.com";
        Optional<User> optionalSuperAdmin = userRepository.findByEmail(SUPER_ADMIN_EMAIL);

        if (optionalRole.isEmpty() || optionalSuperAdmin.isPresent()) {
            return;
        }
        User superAdmin = new User();
        superAdmin.setUsername("super_admin");
        superAdmin.setEmail(SUPER_ADMIN_EMAIL);
        superAdmin.setPassword(passwordEncoder.encode("super_admin123"));
        superAdmin.setRole(optionalRole.get());
        userRepository.save(superAdmin);

    }
}
