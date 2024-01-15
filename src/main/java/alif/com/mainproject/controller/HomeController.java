package alif.com.mainproject.controller;

import alif.com.mainproject.aop.CheckPermission;
import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.entity.News;
import alif.com.mainproject.repository.NewsRepository;
import alif.com.mainproject.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {
    private final NewsRepository newsRepository;

    public HomeController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }
    @Autowired
    NewsService newsService;

//    @CheckPermission(permission = "VIEW_NEWS")
//    @GetMapping("")
//    public ResponseEntity<?> viewAll(@RequestParam Integer page, @RequestParam Integer size){
//        return ResponseEntity.ok(newsService.viewAll(page, size));
//    }

    @GetMapping("")
    public ResponseEntity<?> viewAl(){
        return ResponseEntity.ok(newsRepository.findAll());
    }


//    @CheckPermission(permission = "VIEW_NEWS")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(newsService.getById(id));
    }

}
