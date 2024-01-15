package alif.com.mainproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nameEn;

    @Column(unique = true)
    private String nameRu;

    @ManyToOne
    private Attachment image;

    @Column(nullable = false)
    private boolean status = true;

    @Column(nullable = false)
    private int ordinal_number;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    Category parentCategory;




}
