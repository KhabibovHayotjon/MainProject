package alif.com.mainproject.repository;

import alif.com.mainproject.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, Long> {

//    boolean existsByTitleEn(String titleEn);
//
//    boolean existsByTitleRu(String titleRu);
}
