package alif.com.mainproject.controller;

import alif.com.mainproject.aop.CheckPermission;
import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.entity.Attachment;
import alif.com.mainproject.repository.AttachmentRepository;
import alif.com.mainproject.service.AttachmentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;

    @CheckPermission(permission = "ADD_IMAGE")
    @PostMapping("/add/image")
    public ResponseEntity<?> add(MultipartHttpServletRequest request) throws IOException {
        ApiResponse response = attachmentService.add(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @CheckPermission(permission = "VIEW_IMAGE")
    @GetMapping("/downloadFile/{id}")
    public  ResponseEntity<?> GetById(HttpServletResponse response,@Valid @PathVariable Long id) throws IOException {
        attachmentService.getById(id, response);
        return ResponseEntity.ok("success");
    }

    @CheckPermission(permission = "VIEW_IMAGE")
    @GetMapping
    public List<Attachment> GetAll(@Valid @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10")Integer size){
        return ResponseEntity.ok(attachmentRepository.findAll(PageRequest.of(page,size)).getContent()).getBody();
    }

    @CheckPermission(permission = "DELETE_IMAGE")
    @DeleteMapping("delAll")
    public ResponseEntity<?> dellAll(){
        attachmentRepository.deleteAll();
        return ResponseEntity.ok("deletedAll");
    }

    @CheckPermission(permission = "DELETE_IMAGE")
    @DeleteMapping("/delId")
    public ResponseEntity<?> delById(@Valid @RequestParam long id){
        attachmentService.DelById(id);
        return ResponseEntity.ok("deleted byId");
    }


}
