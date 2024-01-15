package alif.com.mainproject.repository;

import alif.com.mainproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByNameEn(String nameEn);

    boolean existsByNameRu(String nameRu);

    Optional<Category> findAllByParentCategoryId(Long parentCategory_id);

//    Optional<Category> findAllByParentCategory(Category parentCategory);

//    void deleteAllByParentCategory(Long parentCategory_id);


}
