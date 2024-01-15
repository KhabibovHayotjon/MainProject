package alif.com.mainproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsDto {

    private String titleRu;
    private String textRu;
    private String titleEn;
    private String textEn;
    private boolean status;
    private Long imageId;
    private Long parentCategoryId;


}
