package alif.com.mainproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileNameOrg;

    private String name;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String ContentType;

    public Attachment(String name, long size, String contentType) {
        this.name = name;
        this.size = size;
        ContentType = contentType;
    }
}
