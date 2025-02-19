package cz.exodus.iam.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "OIDC_CLIENT")
public class OidcClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    private ApplicationEntity application;

    @Column(name = "CLIENT_ID", nullable = false)
    private String clientId;

    @Column(name = "GRANT_TYPE", nullable = false)
    private String grantType;
}
