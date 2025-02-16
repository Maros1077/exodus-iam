package cz.exodus.iam.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import cz.exodus.iam.db.model.IdentityOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditAuthPointModel {

    @JsonProperty(required = true)
    private String operation;

    @JsonProperty(required = true)
    private String type;

    @JsonProperty()
    private String newValue;
}
