package cz.exodus.iam.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.exodus.iam.model.AuthPoint;
import cz.exodus.iam.model.IdentityTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @JsonProperty(required = true)
    String application;

    @JsonProperty(required = true)
    IdentityTag identificationTag;

    @JsonProperty(required = true)
    AuthPoint authPoint;

    @JsonProperty(required = true)
    String grantType;

    @JsonProperty(required = true)
    String clientId;

    @JsonProperty(required = true)
    String clientSecret;

    @JsonProperty(required = true)
    String scope;

}