package alif.com.mainproject.controller;

import alif.com.mainproject.aop.CheckPermission;
import alif.com.mainproject.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/role")
public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @CheckPermission(permission = "VIEW_ROLE")
    @GetMapping("/viewAll")
    public ResponseEntity<?> viewAll(){
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
