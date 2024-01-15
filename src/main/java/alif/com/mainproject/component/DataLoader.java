package alif.com.mainproject.component;

import alif.com.mainproject.entity.Role;
import alif.com.mainproject.entity.UserApp;
import alif.com.mainproject.enums.Permissions;
import alif.com.mainproject.repository.RoleRepository;
import alif.com.mainproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static alif.com.mainproject.enums.Permissions.*;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Value(value = "${spring.sql.init.mode}")
    private String sqlInitMode;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        if (sqlInitMode.equals("always")){

            Role roleAdmin = new Role();
            roleAdmin.setName("admin");
            roleAdmin.setPermissions(Set.of(Permissions.values()));
            Role savedRoleAdmin = roleRepository.save(roleAdmin);


            UserApp admin = new UserApp();
            admin.setName("Anis");
            admin.setPhoneNumber("998931460288");
            admin.setEnabled(true);
            admin.setPassword(passwordEncoder.encode("rootAdmin"));
            admin.setRole(savedRoleAdmin);
            userRepository.save(admin);


            Role roleUser = new Role();
            roleUser.setName("userApp");
            roleUser.setPermissions(Set.of(VIEW_NEWS));
            Role saveRoleUser = roleRepository.save(roleUser);

            UserApp userApp = new UserApp();
            userApp.setName("Azamat");
            userApp.setPhoneNumber("998973906790");
            userApp.setEnabled(true);
            userApp.setPassword(passwordEncoder.encode("rootUser"));
            userApp.setRole(saveRoleUser);
            userRepository.save(userApp);



            Role roleModerator = new Role();
            roleModerator.setName("moderator");
            roleModerator.setPermissions(Set.of(ADD_NEWS,EDIT_NEWS,DELETE_NEWS));
            Role saveRoleModerator = roleRepository.save(roleModerator);

            UserApp moderator = new UserApp();
            moderator.setName("Hasan");
            moderator.setPhoneNumber("998933538543");
            moderator.setEnabled(true);
            moderator.setPassword(passwordEncoder.encode("rootModerator"));
            moderator.setRole(saveRoleModerator);
            userRepository.save(moderator);
        }
    }
}
