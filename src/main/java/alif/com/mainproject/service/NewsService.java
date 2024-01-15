package alif.com.mainproject.service;

import alif.com.mainproject.dtos.ApiResponse;
import alif.com.mainproject.dtos.NewsDto;
import alif.com.mainproject.entity.Attachment;
import alif.com.mainproject.entity.Category;
import alif.com.mainproject.entity.News;
import alif.com.mainproject.repository.AttachmentRepository;
import alif.com.mainproject.repository.CategoryRepository;
import alif.com.mainproject.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse addNews(NewsDto newsDto){




        if (newsDto.getParentCategoryId() != null) {
            if (newsDto.getTitleRu() != null || newsDto.getTitleEn() != null) {

                News news = new News();
                if (newsDto.getImageId() != null) {
                    Optional<Attachment> attachment = attachmentRepository.findById(newsDto.getImageId());
                    if (attachment.isEmpty()) {
                        return new ApiResponse("Attachment not found", false, 409, null);
                    }
                    news.setImage(attachment.get());
                }

                if (newsDto.getParentCategoryId() != null) {
                    Optional<Category> category = categoryRepository.findById(newsDto.getParentCategoryId());
                    if (category.isEmpty()) {
                        return new ApiResponse("Category not found", false, 409, null);
                    }
                    if (category.get().getParentCategory() != null) {
                        news.setParentCategory(category.get());
                    }
//                    return new ApiResponse("Category not found", false, 409, null);
                }


                if (newsDto.getTitleEn() != null) {
                    news.setTitleEn(newsDto.getTitleEn());
                }

                if (newsDto.getTitleRu() != null) {
                    news.setTitleEn(newsDto.getTitleRu());
                }


                if (newsDto.getTextEn() != null) {
                    news.setTextEn(newsDto.getTextEn());
                }

                if (newsDto.getTextRu() != null) {
                    news.setTextEn(newsDto.getTextRu());
                }


                news.setStatus(newsDto.isStatus());
                News save = newsRepository.save(news);
                return new ApiResponse("create", true, 201, save);
            }
            return new ApiResponse("Title can not be null",false,409,null);
        }
        return new ApiResponse("not create",false,409,null);
    }
//    public List<News> viewAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10")Integer size ){
//        return newsRepository.findAll(PageRequest.of(page,size)).getContent();
//    }

    public News getById(Long id){
        Optional<News> optional = newsRepository.findById(id);
        return optional.orElse(null);
    }


    public ApiResponse edit(NewsDto newsDto,  Long id){
        if (newsDto.getTitleRu() != null || newsDto.getTitleEn() != null){
            Optional<News> optionalNews = newsRepository.findById(id);
            News news = optionalNews.get();

            if (newsDto.getImageId() != null) {
                Optional<Attachment> attachment = attachmentRepository.findById(newsDto.getImageId());
                if (attachment.isEmpty()) {
                    return new ApiResponse("Attachment not found", false, 409, null);
                }
                news.setImage(attachment.get());
            }

            if (newsDto.getParentCategoryId() != null) {
                Optional<Category> category = categoryRepository.findById(newsDto.getParentCategoryId());
                if (category.isEmpty()) {
                    return new ApiResponse("Category not found", false, 409, null);
                }
                if (category.get().getParentCategory() != null) {
                    news.setParentCategory(category.get());
                }
            }

            if (newsDto.getTitleEn() != null) {
                news.setTitleEn(newsDto.getTitleEn());
            }

            if (newsDto.getTitleRu() != null) {
                news.setTitleEn(newsDto.getTitleRu());
            }


            if (newsDto.getTextEn() != null) {
                news.setTextEn(newsDto.getTextEn());
            }

            if (newsDto.getTextRu() != null) {
                news.setTextEn(newsDto.getTextRu());
            }



            news.setStatus(newsDto.isStatus());
            News save = newsRepository.save(news);
            return new ApiResponse("create",true,201,save);
        }
        return new ApiResponse("not create",false,409,null);
    }

    public ApiResponse delById(Long id){
        Optional<News> optional = newsRepository.findById(id);
        if (optional.isPresent()){
            newsRepository.deleteById(id);
            return new ApiResponse("delete",true,200,null);
        }
        return new ApiResponse("not found",false,400,null);
    }


}
