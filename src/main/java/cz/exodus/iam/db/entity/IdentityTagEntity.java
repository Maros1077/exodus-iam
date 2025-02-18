package cz.exodus.iam.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "IDENTITY_TAG")
public class IdentityTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "IDENTITY_ID", nullable = false)
    private IdentityEntity identity;

    @JoinColumn(name = "TAG_TYPE_ID", nullable = false)
    @ManyToOne
    private TagTypeEntity tagType;

    @Column(name = "TAG_VALUE", nullable = false)
    private String tagValue;

    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    @ManyToOne
    private ApplicationEntity application;
}
