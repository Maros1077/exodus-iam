package cz.exodus.iam.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "AUTH_POINT_TYPE")
public class AuthPointTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "REQUIRE_VALUE", nullable = false)
    private Boolean requireValue;
}
