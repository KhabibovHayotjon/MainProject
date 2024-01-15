package alif.com.mainproject.controller;

import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.ApiResponseWithToken;
import alif.com.mainproject.dtos.RegisterUserDto;
import alif.com.mainproject.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto registerUserDto){
        ApiResponse register = authService.register(registerUserDto);
        return ResponseEntity.status(register.getCode()).body(register);
    }

    @PostMapping("/verifyUser")
    public ResponseEntity<?> verify(@Valid @RequestParam() String phoneNumber,@RequestParam String code){
        ApiResponseWithToken verify = authService.verifyUser(phoneNumber,code);
        return ResponseEntity.status(verify.getCode()).body(verify);
    }

    @PostMapping("/resendCode")
    public ResponseEntity<?> resendCode( @RequestParam String phoneNumber){
        ApiResponse resend = authService.resendCode(phoneNumber);
        return ResponseEntity.status(resend.getCode()).body(resend);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestParam String phoneNumber,@RequestParam String password){
        ApiResponseWithToken login = authService.login(phoneNumber,password);
        return ResponseEntity.status(login.getCode()).body(login);
    }

    @PostMapping
    public ResponseEntity<?> logout(){
        ApiResponse response = authService.logout();
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
