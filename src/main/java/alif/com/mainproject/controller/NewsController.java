package alif.com.mainproject.controller;

import alif.com.mainproject.aop.CheckPermission;
import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.NewsDto;
import alif.com.mainproject.repository.NewsRepository;
import alif.com.mainproject.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    NewsService newsService;
    @Autowired
    private NewsRepository newsRepository;


    @CheckPermission(permission = "ADD_NEWS")
    @PostMapping("/addNews")
    public ResponseEntity<?> AddNews(@Valid @RequestBody NewsDto newsDto){
        ApiResponse response = newsService.addNews(newsDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @CheckPermission(permission = "EDIT_NEWS")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editNews(@Valid @RequestBody NewsDto newsDto, @PathVariable Long id){
        ApiResponse response = newsService.edit(newsDto,id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @CheckPermission(permission = "DELETE_NEWS")
    @DeleteMapping("/dellById/{id}")
    public ResponseEntity<?> dellById(@PathVariable Long id){
        ApiResponse response = newsService.delById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @CheckPermission(permission = "DELETE_NEWS")
    @DeleteMapping("/delAll")
    public ApiResponse dellAll(){
        return new ApiResponse("delete All news",true,200,null);
    }



}
