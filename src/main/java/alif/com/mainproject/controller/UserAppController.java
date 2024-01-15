package alif.com.mainproject.controller;

import alif.com.mainproject.aop.CheckPermission;
import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.UsersDto;
import alif.com.mainproject.repository.UserRepository;
import alif.com.mainproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")

public class UserAppController {
    private final UserRepository userRepository;

    @Autowired
    UserService userService;

    public UserAppController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @CheckPermission(permission = "VIEW_USER")
    @GetMapping("/viewAll")
    public ResponseEntity<?> viewAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @CheckPermission(permission = "ADD_USER")
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UsersDto usersDto){
        ApiResponse add = userService.add(usersDto);
        return  ResponseEntity.status(add.getCode()).body(add);
    }

}
