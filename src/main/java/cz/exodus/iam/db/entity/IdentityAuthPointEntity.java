package cz.exodus.iam.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "IDENTITY_AUTH_POINT")
public class IdentityAuthPointEntity {

    static final String SEQ_NAME = "IDENTITY_AUTH_POINT_ID_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(sequenceName = SEQ_NAME, name = SEQ_NAME, allocationSize = 1)
    @Column(name = "ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "IDENTITY_ID", nullable = false)
    private IdentityEntity identity;

    @JoinColumn(name = "AUTH_POINT_TYPE_ID", nullable = false)
    @ManyToOne
    private AuthPointTypeEntity authPointType;

    @Column(name = "AUTH_POINT_VALUE")
    private String authPointValue;

    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    @ManyToOne
    private ApplicationEntity application;
}
