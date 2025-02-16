package cz.exodus.iam.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.exodus.iam.model.IdentityTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveIdentityResponse {

    @JsonProperty(required = true)
    String idid;

    @JsonProperty(required = true)
    String application;

    @JsonProperty()
    List<IdentityTag> tags;

    @JsonProperty()
    List<String> authPoints;
}