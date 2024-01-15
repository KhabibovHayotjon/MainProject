package alif.com.mainproject.controller;

import alif.com.mainproject.aop.CheckPermission;
import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.CategoryDto;
import alif.com.mainproject.entity.Category;
import alif.com.mainproject.repository.CategoryRepository;
import alif.com.mainproject.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;


    @CheckPermission(permission = "ADD_CATEGORY")
    @PostMapping("/addCate")
    public ResponseEntity<?> add(@Valid @RequestBody CategoryDto categoryDto){
        ApiResponse response = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @CheckPermission(permission = "EDIT_CATEGORY")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long id){
        ApiResponse response = categoryService.edit(categoryDto,id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @CheckPermission(permission = "VIEW_CATEGORY")
    @GetMapping("/getAll")
    public List<Category> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10")Integer size){
        return ResponseEntity.ok(categoryRepository.findAll(PageRequest.of(page,size)).getContent()).getBody();
    }

    @CheckPermission(permission = "VIEW_CATEGORY")
    @GetMapping("/getByParentCategory/{id}")
    public Optional<Category> getByPar(@PathVariable long id){
        return ResponseEntity.ok(categoryRepository.findAllByParentCategoryId(id)).getBody();
    }

    @CheckPermission(permission = "VIEW_CATEGORY")
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }


    @CheckPermission(permission = "DELETE_CATEGORY")
    @DeleteMapping("/delById/{id}")
    public ResponseEntity<?> delById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.delById(id));
    }

    @CheckPermission(permission = "DELETE_CATEGORY")
    @DeleteMapping("/delParCat/{id}")
    public ResponseEntity<?> delByParCat(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.delParCat(id));
    }
}
