package alif.com.mainproject.service;

import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.UsersDto;
import alif.com.mainproject.entity.UserApp;
import alif.com.mainproject.exception.UserResourceNotFoundException;
import alif.com.mainproject.repository.RoleRepository;
import alif.com.mainproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserService(RoleRepository roleRepository,
                       UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse add(UsersDto usersDto){
        UserApp user = new UserApp();
        user.setPhoneNumber(usersDto.getPhoneNumber());
        user.setName(usersDto.getName());
        user.setRole(roleRepository.findById(usersDto.getRoleId()).orElseThrow(() -> new UserResourceNotFoundException("role not found")));
        user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        user.setEnabled(true);
        UserApp userApp = userRepository.save(user);
        return new ApiResponse("create",true,200,userApp);
    }



}
