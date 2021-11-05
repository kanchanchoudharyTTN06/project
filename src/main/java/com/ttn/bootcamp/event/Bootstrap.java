package com.ttn.bootcamp.event;

import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.repository.RoleRepository;
import com.ttn.bootcamp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class Bootstrap implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() < 1) {
            User admin = new User();
            admin.setFirstName("Kanchan");
            admin.setLastName("Choudhary");
            admin.setEmail("kanchan.choudhary@tothenew.com");
            admin.setPassword(passwordEncoder.encode("kanchan06"));
            admin.setActive(true);
            admin.setDeleted(false);

            Role role_admin = new Role();
            role_admin.setAuthority("ROLE_ADMIN");

            Role role_seller = new Role();
            role_seller.setAuthority("ROLE_SELLER");

            Role role_customer = new Role();
            role_customer.setAuthority("ROLE_CUSTOMER");

            admin.setRoleList(Arrays.asList(role_admin));

            //role_admin.setUserList(Arrays.asList(admin));

            roleRepository.saveAll(Arrays.asList(role_customer, role_seller, role_admin));

            userRepository.save(admin);

        }
    }
}
