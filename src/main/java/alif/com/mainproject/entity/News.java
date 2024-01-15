package alif.com.mainproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "text")
    private String titleRu;

    private String textRu;


    @Column(columnDefinition = "text")
    private String titleEn;

    private String textEn;


    @Column(nullable = false)
    private boolean status = true;


    @ManyToOne
     Attachment image;

    @ManyToOne
    @JoinColumn(nullable = false)
     Category ParentCategory;


    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updateAt;

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updateBy;

    public News(String title, String text, boolean status, Long imageId, Long parentCategoryId) {

    }


    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    @CreatedBy
//    private UserApp createdBy;
}
