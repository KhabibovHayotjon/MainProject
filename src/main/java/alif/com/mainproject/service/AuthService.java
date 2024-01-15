package alif.com.mainproject.service;

import alif.com.mainproject.configuration.SecurityConfiguration;
import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.ApiResponseWithToken;
import alif.com.mainproject.dtos.RegisterUserDto;
import alif.com.mainproject.entity.TokenBlockList;
import alif.com.mainproject.entity.UserApp;
import alif.com.mainproject.exception.AppLoginException;
import alif.com.mainproject.exception.UserResourceNotFoundException;
import alif.com.mainproject.repository.RoleRepository;
import alif.com.mainproject.repository.TokenBlockListRepository;
import alif.com.mainproject.repository.UserRepository;
import alif.com.mainproject.security.JwtService;
import alif.com.mainproject.service.smsApiService.SmsApiService;
import alif.com.mainproject.utils.AppUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    SmsApiService smsApiService;

    @Lazy
    @Autowired
    JwtService jwtService;
    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    @Lazy
    @Autowired
    SecurityConfiguration securityConfiguration;

    @Autowired
    HttpServletRequest httpServletRequest;
    private final TokenBlockListRepository tokenBlockListRepository;

    public AuthService(UserRepository userRepository,
                       TokenBlockListRepository tokenBlockListRepository) {
        this.userRepository = userRepository;
        this.tokenBlockListRepository = tokenBlockListRepository;
    }

    public ApiResponse register(RegisterUserDto registerUserDto){
        if (userRepository.existsByPhoneNumber(registerUserDto.getPhoneNumber()))
        return new ApiResponse("phone number already exits",false,409,null);
        UserApp userApp = new UserApp();
        userApp.setName(registerUserDto.getName());
        userApp.setPhoneNumber(registerUserDto.getPhoneNumber());
        userApp.setPassword(securityConfiguration.passwordEncoder().encode(registerUserDto.getPassword()));
        userApp.setRole(roleRepository.findByName("userApp").orElseThrow( () -> new UserResourceNotFoundException("rolName userApp not found")));
        String code = AppUtils.generateCode();
        smsApiService.SendSms(registerUserDto.getPhoneNumber(), code);
        userApp.setVerificationCode(code);
        UserApp save = userRepository.save(userApp);
        return new ApiResponse("created",true,201,save);
    }

    public ApiResponseWithToken verifyUser(String phoneNumber, String code) {
        UserApp userApp = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserResourceNotFoundException("userApp not found"));
        if (userApp.getVerificationCode().equals(code)){
            userApp.setVerificationCode(null);
            userApp.setEnabled(true);
            userRepository.save(userApp);
            return new ApiResponseWithToken("verified",true,200, jwtService.generateToken(userApp.getPhoneNumber()));
        }
        return new ApiResponseWithToken("verification code is incorrect",false,400,null);
    }

    public ApiResponse resendCode(String phoneNumber) {
        UserApp userApp = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserResourceNotFoundException("userApp not found"));
        String code = AppUtils.generateCode();
        userApp.setVerificationCode(code);
        smsApiService.SendSms(userApp.getPhoneNumber(), code);
        userRepository.save(userApp);
        return new ApiResponse("verified", true, 200, jwtService.generateToken(userApp.getPhoneNumber()));

    }

    public ApiResponseWithToken login(String phoneNumber,String password) {
        try {
            UserApp principal =(UserApp) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password)).getPrincipal();
            return new  ApiResponseWithToken("success",true,200, jwtService.generateToken(principal.getPhoneNumber()));
        }catch (Exception e){
            throw  new AppLoginException("password or phoneNumber is incorrect");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserResourceNotFoundException("user not found"));
    }

    public ApiResponse logout(){
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")){
            authorization = authorization.substring(7);
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            tokenBlockListRepository.save(new TokenBlockList(null,authorization,jwtService.extractExpiration(authorization)));
            return new ApiResponse("logged out",true,200,null);
        }
        return new ApiResponse("something want wrong",false,400,null);
    }

}
