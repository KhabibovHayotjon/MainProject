package alif.com.mainproject.service;

import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.entity.Attachment;
import alif.com.mainproject.repository.AttachmentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

@Service
public class AttachmentService {
//    @Autowired
//    AttachmentContentRepository attachmentContentRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    private static final String uploadDirectory = "C:\\Users\\user\\Desktop\\Folder_pro";

    public ApiResponse add(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> filenames = request.getFileNames();
        MultipartFile file = request.getFile(filenames.next());

//        Iterator<String> fileNames = request.getFileNames();
//        MultipartFile file = request.getFile(fileNames.next());

        Attachment attachment = new Attachment();
        assert file != null;
        String fileOrgName = file.getOriginalFilename();
        attachment.setFileNameOrg(fileOrgName);
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
//        String[] split = attachment.getFileNameOrg().split("\\.");
//        String s = UUID.randomUUID().toString();
//        s = s+"."+split[(split.length-1)];
//        attachment.setName(s);
        Attachment save = attachmentRepository.save(attachment);
        if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            Path path = Paths.get(uploadDirectory + "/" + fileOrgName);
            Files.copy(file.getInputStream(), path);
        }
        return new ApiResponse("Success",true,201,save);
    }

    public void getById(@RequestParam long id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();

            response.setHeader("Content-Disposition","attachment: filename=\""+attachment.getFileNameOrg()+"\"");
            response.setContentType(attachment.getContentType());
            FileInputStream fileInputStream = new FileInputStream(uploadDirectory+ "/"+attachment.getName());
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        }
    }

    public String DelById(@RequestParam long id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            attachmentRepository.deleteById(id);
            return "deleted";
        }else {return "not found";}
    }


}
