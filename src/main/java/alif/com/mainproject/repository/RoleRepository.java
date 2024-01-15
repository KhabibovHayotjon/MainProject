package alif.com.mainproject.repository;

import alif.com.mainproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);


}
