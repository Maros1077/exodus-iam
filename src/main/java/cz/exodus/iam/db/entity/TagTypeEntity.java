package cz.exodus.iam.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TAG_TYPE")
public class TagTypeEntity {

    static final String SEQ_NAME = "TAG_TYPE_ID_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", nullable = false)
    private String name;
}
