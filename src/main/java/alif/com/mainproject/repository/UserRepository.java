package alif.com.mainproject.repository;

import alif.com.mainproject.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<UserApp> findByPhoneNumber(String phoneNumber);
}
