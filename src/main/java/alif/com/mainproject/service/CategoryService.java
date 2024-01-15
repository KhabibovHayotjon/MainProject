package alif.com.mainproject.service;

import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.CategoryDto;
import alif.com.mainproject.entity.Attachment;
import alif.com.mainproject.entity.Category;
import alif.com.mainproject.repository.AttachmentRepository;
import alif.com.mainproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public ApiResponse addCategory(CategoryDto categoryDto) {
        if (categoryDto.getNameRu() != null || categoryDto.getNameEn() != null){
            if (categoryRepository.existsByNameRu(categoryDto.getNameRu()) && categoryDto.getNameEn() == null) {
                return new ApiResponse("Category already exists", false, 409, null);
            }

        if (categoryRepository.existsByNameEn(categoryDto.getNameEn()) && categoryDto.getNameRu() == null) {
            return new ApiResponse("Category already exists", false, 409, null);
        }

        Category category1 = new Category();
        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (optionalCategory.isEmpty()) {
                return new ApiResponse("ParentCategory not found", false, 409, null);
            }

            if (optionalCategory.get().getParentCategory() == null) {
                category1.setParentCategory(optionalCategory.get());
            }
        }
//        if (categoryDto.getParentCategoryId() == null) {
//            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
//            if (optionalCategory.isEmpty()) {
//                return new ApiResponse("ParentCategory not found", false, 409, null);
//            }
//        }
        if (categoryDto.getImage() != null) {
            Optional<Attachment> attachment = attachmentRepository.findById(categoryDto.getImage());
            if (attachment.isEmpty()) {
                return new ApiResponse("Attachment not found", false, 409, null);
            }
            category1.setImage(attachment.get());
        }

        if (categoryDto.getNameRu() != null) {
            category1.setNameRu(categoryDto.getNameRu());
        }

        if (categoryDto.getNameEn() != null) {
            category1.setNameEn(categoryDto.getNameEn());
        }


        category1.setStatus(categoryDto.isStatus());
        category1.setOrdinal_number(categoryDto.getOrdinal_number());
        Category save = categoryRepository.save(category1);
        return new ApiResponse("success", true, 201, save);


    }
        return new ApiResponse("Category name can not be null",false,409,null);

}

    public ApiResponse edit(CategoryDto categoryDto, Long id) {
        if (categoryDto.getNameRu() != null || categoryDto.getNameEn() != null){
            if (categoryRepository.existsByNameEn(categoryDto.getNameEn()) && categoryDto.getNameRu() == null) {
                return new ApiResponse("Category already exists", false, 409, null);
            }

        if (categoryRepository.existsByNameRu(categoryDto.getNameRu()) && categoryDto.getNameEn() == null) {
            return new ApiResponse("Category already exists", false, 409, null);
        }

        Optional<Category> optional = categoryRepository.findById(id);
        Category category = optional.get();


        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (optionalCategory.isEmpty()) {
                return new ApiResponse("ParentCategory not found", false, 409, null);
            }

            if (categoryDto.getParentCategoryId() != null) {
//                Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
                if (optionalCategory.isEmpty()) {
                    return new ApiResponse("ParentCategory not found", false, 409, null);
                }
                if (optionalCategory.get().getParentCategory() == null) {
                    category.setParentCategory(optionalCategory.get());
                }
            }
//            if (categoryDto.getParentCategoryId() == null) {
////                Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
//                if (optionalCategory.isEmpty()) {
//                    return new ApiResponse("ParentCategory not found", false, 409, null);
//                }
//            }


            if (categoryDto.getImage() != null) {
                Optional<Attachment> attachment = attachmentRepository.findById(categoryDto.getImage());
                if (attachment.isEmpty()) {
                    return new ApiResponse("Attachment not found", false, 409, null);
                }
                category.setImage(attachment.get());
            }
            if (categoryDto.getNameRu() != null) {
                category.setNameRu(categoryDto.getNameRu());
            }

            if (categoryDto.getNameEn() != null) {
                category.setNameEn(categoryDto.getNameEn());
            }


            category.setParentCategory(optional.get());
//            category.setImage(categoryDto.getImage());
            category.setStatus(categoryDto.isStatus());
            category.setOrdinal_number(categoryDto.getOrdinal_number());
            Category save = categoryRepository.save(category);
            return new ApiResponse("success", true, 201, save);


        }

        if (categoryDto.getImage() != null) {
            Optional<Attachment> attachment = attachmentRepository.findById(categoryDto.getImage());
            if (attachment.isEmpty()) {
                return new ApiResponse("Attachment not found", false, 409, null);
            }
            category.setImage(attachment.get());
        }

        if (categoryDto.getNameRu() != null) {
            category.setNameRu(categoryDto.getNameRu());
        }

        if (categoryDto.getNameEn() != null) {
            category.setNameEn(categoryDto.getNameEn());
        }


//            category.setName(categoryDto.getName());
//            category.setImage(categoryDto.getImage());
        category.setStatus(categoryDto.isStatus());
        category.setOrdinal_number(categoryDto.getOrdinal_number());
        Category save = categoryRepository.save(category);
        return new ApiResponse("success", true, 201, save);
    }
        return new ApiResponse("Category name can not be null",false,409,null);
    }

    public Category getById(@PathVariable Long id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }
    public ApiResponse delById(@PathVariable Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent() && categoryRepository.findAllByParentCategoryId(id).isEmpty()){
            categoryRepository.deleteById(id);
            return new ApiResponse("deleted",true,200,null);
        }
        return new ApiResponse("Category not deleted",false,409,null);
    }

    public ApiResponse delParCat(@PathVariable Long id){
        Optional<Category> category = categoryRepository.findAllByParentCategoryId(id);
        if (category.isEmpty()){
            return new ApiResponse("ParentCategory not found",false,409,null);
        }
        categoryRepository.deleteById(category.get().getId());
        return new ApiResponse("deleted ParentCategory",true,201,null);
    }

}