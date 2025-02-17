package com.cenfo.tech.task1.config;

import com.cenfo.tech.task1.entity.Role;
import com.cenfo.tech.task1.entity.RoleEnum;
import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.services.role.IRoleService;
import com.cenfo.tech.task1.services.user.IUserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final IRoleService roleService;
    private final IUserService userService;

    public UserSeeder(IRoleService roleService, IUserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createRolesIfNotExist();
        this.createDefaultUsers();
    }

    private void createRolesIfNotExist() {
        if (roleService.findByName(RoleEnum.USER).isEmpty()) {
            Role userRole = new Role();
            userRole.setName(RoleEnum.USER);
            userRole.setDescription(RoleEnum.USER.name());
            roleService.register(userRole);
        }

        if (roleService.findByName(RoleEnum.SUPER_ADMIN).isEmpty()) {
            Role superAdminRole = new Role();
            superAdminRole.setName(RoleEnum.SUPER_ADMIN);
            superAdminRole.setDescription(RoleEnum.SUPER_ADMIN.name());
            roleService.register(superAdminRole);
        }
    }

    private void createDefaultUsers() {
        createSuperAdmin();
        createUser();
    }

    private void createUser() {
        Optional<Role> optionalRole = roleService.findByName(RoleEnum.USER);
        String USER_EMAIL = "user1@gmail.com";
        Optional<User> optionalUser = userService.findByEmail(USER_EMAIL);

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail(USER_EMAIL);
        user1.setPassword("user123");
        user1.setRole(optionalRole.get());
        userService.register(user1);
    }

    private void createSuperAdmin() {

        Optional<Role> optionalRole = roleService.findByName(RoleEnum.SUPER_ADMIN);
        String SUPER_ADMIN_EMAIL = "super_admin@gmail.com";
        Optional<User> optionalSuperAdmin = userService.findByEmail(SUPER_ADMIN_EMAIL);

        if (optionalRole.isEmpty() || optionalSuperAdmin.isPresent()) {
            return;
        }
        User superAdmin = new User();
        superAdmin.setUsername("super_admin");
        superAdmin.setEmail(SUPER_ADMIN_EMAIL);
        superAdmin.setPassword("super_admin123");
        superAdmin.setRole(optionalRole.get());
        userService.register(superAdmin);

    }
}
