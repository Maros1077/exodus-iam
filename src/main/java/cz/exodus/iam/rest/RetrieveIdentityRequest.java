package cz.exodus.iam.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.exodus.iam.db.model.TagType;
import cz.exodus.iam.model.IdentityTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveIdentityRequest {

    @JsonProperty(required = true)
    String application;

    @JsonProperty(required = true)
    IdentityTag identificationTag;

    @JsonProperty()
    boolean retrieveTags;

    @JsonProperty()
    boolean retrieveAuthPoints;
}