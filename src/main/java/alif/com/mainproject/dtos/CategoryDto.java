package alif.com.mainproject.dtos;

import alif.com.mainproject.entity.Attachment;
import alif.com.mainproject.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

//    @NotBlank(message = "name can not be empty")
    private String nameEn;

    private String nameRu;

    private Long image;

//    @NotBlank(message = "status can not be empty")
    private boolean status;

//    @NotBlank(message = "ordinal number can not be empty")
    private int ordinal_number;


    private Long parentCategoryId;
}
