package alif.com.mainproject.repository;

import alif.com.mainproject.entity.TokenBlockList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface TokenBlockListRepository extends JpaRepository<TokenBlockList, Long> {

    boolean existsByToken(String token);

    void deleteAllByExpireDateIsBefore(Date expireDate);
}