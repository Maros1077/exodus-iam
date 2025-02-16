package cz.exodus.iam.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.exodus.iam.model.AuthPoint;
import cz.exodus.iam.model.EditAuthPointModel;
import cz.exodus.iam.model.EditTagModel;
import cz.exodus.iam.model.IdentityTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIdentityRequest {

    @JsonProperty(required = true)
    String application;

    @JsonProperty(required = true)
    IdentityTag identificationTag;

    @JsonProperty()
    List<EditTagModel> tags;

    @JsonProperty()
    List<EditAuthPointModel> authPoints;

}